package RecycleViewHelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import bryan.miranda.crudbryan1b.R
import modelo.Dat0aClassMusica

class Adapter(var Datos: List<Dat0aClassMusica>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Conectar el RecyclerView con la card
        val Vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card,parent, false)
        return ViewHolder(Vista)
    }

    override fun getItemCount() = Datos.size
        //Devuelve la cantidad de valores que se muestran




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Poder darle clic a los elementos de la card
        val Item = Datos[position]
        holder.txtNombre.text = Item.NombreCancion
    }
}