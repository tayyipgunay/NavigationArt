package com.tayyipgunay.navigationart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tayyipgunay.navigationart.databinding.RecyclerRowBinding
import com.tayyipgunay.navigationart.model.Place
import com.tayyipgunay.navigationart.view.ArtListFragmentDirections
import io.reactivex.rxjava3.core.Flowable

class artAdapter(val artList:List<Place>): RecyclerView.Adapter<artAdapter.Artholder>() {

    class Artholder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Artholder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Artholder(binding)

    }



    override fun getItemCount(): Int {
        return artList.count()

    }

    override fun onBindViewHolder(holder: Artholder, position: Int) {
        holder.binding.metin.text=artList.get(position).artName // fragementdan room ile veri çekip artListe ekleyeceğiz ve göstereceğiz
        //id ile de tıklandıktan sonra ona göre resmi göstereceğiz.
holder.itemView.setOnClickListener{
    it->
  val id =  artList.get(position).id
    //val idArray=intArrayOf(id)
    val action = ArtListFragmentDirections.actionArtListFragmentToDetailsFragment("old",id)

    Navigation.findNavController(it).navigate(action)


  /*  artList: Place nesnelerinden oluşan bir liste.
    position, artList içindeki her Place nesnesinin sırasını belirtir.
    Her bir öğe: Place sınıfının bir örneği. RecyclerView'da göstermek için Place nesnelerinin verilerini kullanıyorsunuz.*/


}
    }
}