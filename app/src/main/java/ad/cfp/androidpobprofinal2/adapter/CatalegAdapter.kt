package ad.cfp.androidpobprofinal2.adapter

import ad.cfp.androidpobprofinal2.R
import ad.cfp.androidpobprofinal2.data.Cataleg
import ad.cfp.androidpobprofinal2.databinding.ItemCatalegBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CatalegAdapter (val llista: List<Cataleg>): RecyclerView.Adapter<CatalegAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): CatalegAdapter.ViewHolder {
        return ViewHolder.crear(p0)
    }

    override fun onBindViewHolder(p0: CatalegAdapter.ViewHolder, p1: Int) {
        p0.bind(llista[p1])
    }

    override fun getItemCount(): Int {
        return llista.size
    }
    class ViewHolder(val binding: ItemCatalegBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Cataleg){
            binding.tvNom.text=item.nom
            binding.tvDescripcio.text=item.descripcio
            binding.tvCategoria.text=item.categoria
            binding.tvValue.text=item.valor.toString()
            if(item.favorite){
                binding.btnBook.setIconTintResource(R.color.yellow)
            } else {
                binding.btnBook.setIconTintResource(R.color.grey)
            }
        }
        companion object{
            fun crear(parent: ViewGroup):ViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= ItemCatalegBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}