package com.tayyipgunay.navigationart.view

import android.os.Bundle
import android.os.UserHandle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.tayyipgunay.navigationart.R
import com.tayyipgunay.navigationart.adapter.artAdapter
import com.tayyipgunay.navigationart.view.ArtListFragmentDirections
import com.tayyipgunay.navigationart.databinding.FragmentArtListBinding
import com.tayyipgunay.navigationart.model.Place
import com.tayyipgunay.navigationart.roomdb.PlaceDao
import com.tayyipgunay.navigationart.roomdb.PlaceDataBase
//import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//import io.reactivex.schedulers.Schedulers


class ArtListFragment : Fragment() {

    private var _binding: FragmentArtListBinding? = null
    private val binding get() = _binding!!
    private val compositeDisposable= CompositeDisposable()
    private lateinit var db: PlaceDataBase
    private lateinit var placeDao: PlaceDao
 // private var arrayList=ArrayList<Place>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Doğru şekilde inflate edin
        _binding = FragmentArtListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        db = Room.databaseBuilder(requireContext(), PlaceDataBase::class.java, "Place").build()
        placeDao = db.placeDao()
        CoroutineScope(Dispatchers.IO).launch {
            val placeList = placeDao.getAll()//suspend fonksyion kullanarak eriştik.
    //val place=placeDao.getAll()
            withContext(Dispatchers.Main) {
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        /*     arrayList.add(place)
                val adapter = artAdapter(arrayList)
                  binding.recyclerView.adapter = adapter*/

               val adapter = artAdapter(placeList)
                binding.recyclerView.adapter = adapter
            }
            /*compositeDisposable.addAll(
    placeDao.getAll()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponse)
)*/




        }
    }

    private fun handleResponse(placeList:List<Place>){
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        val adapter=artAdapter(placeList)
        binding.recyclerView.adapter=adapter

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.artAddid) {
            // menü ekleme seçilmişse yapılacaklar
           // val info="new"
var id=0//default değer


            val action = ArtListFragmentDirections.actionArtListFragmentToDetailsFragment("new",id)

            Navigation.findNavController(binding.root).navigate(action)
//fragment arası veri gönderimi


        }

        return true


    }


}



