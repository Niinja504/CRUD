package bryan.miranda.crudbryan1b

import RecycleViewHelper.Adapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.Dat0aClassMusica

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandar a llamar a todos los elementos
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtDuracion = findViewById<EditText>(R.id.txtDuracion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        //Mando a llamar al RecyclerView
        val rcvMusica = findViewById<RecyclerView>(R.id.rcvMusica)
        //Asignarle un layout al RecyclerView
        rcvMusica.layoutManager = LinearLayoutManager(this)

        //TODO: Mostrar todo

        fun MostrarDatos(): List<Dat0aClassMusica> {
            //1) Creo un objeto de la clase conexión
            val ObjectConexion = ClaseConexion().cadenaConexion()


            //Creo un Statement
            val Statement = ObjectConexion?.createStatement()
            val ResultSet = Statement?.executeQuery("SELECT * FROM tbMusica")!!

            //Los simbolos de interrogracion "?" y "!!" es para los datos nulos que pueden regresar de la BD

            //Voy  a guardar todo lo que me traiga el select
            val Canciones = mutableListOf<Dat0aClassMusica>()

            while (ResultSet.next()){
                val Nombre = ResultSet.getString("NombreCancio    n")
                val Cancion = Dat0aClassMusica(Nombre)
                Canciones.add(Cancion)
            }
            return Canciones

        }

        //Asignar el adaptador al RecyclerView
        //Ejecutar la función de mostrar datos
        CoroutineScope(Dispatchers.IO).launch{
            //Creo una variable que ejecute la función de mostrar datos
            val MusicaDB = MostrarDatos()
            withContext(Dispatchers.Main){
                val MiAdaptador = Adapter(MusicaDB)
                rcvMusica.adapter = MiAdaptador
            }
        }

        //2- Programar el boton
        btnAgregar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1- Crear un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Crear una variable que contenga un PrepareStatement
                val addMusica = objConexion?.prepareStatement("insert into tbMusica values(?, ?, ?)")!!

                addMusica.setString(1, txtNombre.text.toString())
                addMusica.setInt(2, txtDuracion.text.toString().toInt())
                addMusica.setString(3, txtAutor.text.toString())
                addMusica.executeUpdate()




            }


        }


    }
}