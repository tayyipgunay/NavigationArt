package com.tayyipgunay.navigationart.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.Transliterator.Position
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.transition.Visibility
import com.tayyipgunay.navigationart.adapter.artAdapter
import com.tayyipgunay.navigationart.databinding.FragmentDetailsBinding
import com.tayyipgunay.navigationart.model.Place
import com.tayyipgunay.navigationart.roomdb.PlaceDao
import com.tayyipgunay.navigationart.roomdb.PlaceDataBase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream


class DetailsFragment : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>//galeriyegitme
    private lateinit var permissionLauncher: ActivityResultLauncher<String>//izin
    var selectedBitmap: Bitmap? = null
    private lateinit var db: PlaceDataBase
    private lateinit var placeDao: PlaceDao
    val compositeDisposable=CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Doğru şekilde inflate edin
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
        // db = Room.databaseBuilder(requireContext(), PlaceDataBase::class.java, "Place").build()//erişim yok.
    }


//placeDao=db.


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        compositeDisposable.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        db = Room.databaseBuilder(requireContext(), PlaceDataBase::class.java, "Place").build()//Veritabanı ismi
        placeDao = db.placeDao()

        registerLauncher()


    val argument = arguments
    argument?.let {
        bundle ->
        val info = DetailsFragmentArgs.fromBundle(bundle).info
        if (info.equals("new")) {
            binding.imageView.setImageResource(0)
            binding.yearid.setText("")
            binding.artistNameid.setText("")
            binding.artNameid.setText("")
            binding.saveid.visibility = View.VISIBLE

        } else {
            binding.saveid.visibility = View.INVISIBLE
            val id = DetailsFragmentArgs.fromBundle(bundle).idinfo

            compositeDisposable.addAll(//Neden Bunu kullandık
                placeDao.loadAllByIds(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse2)
            )


        }

    }




        binding.saveid.setOnClickListener { saveTick ->
ınsertAll()

        }



        binding.imageView.setOnClickListener { imageTick ->
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //İzin Yokken.
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                // izin vere basarsan galeriye gider.
                //izin verme basarsan izin reddedildi der.

                //  if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                /*if (ActivityCompat.shouldShowRequestPermissionRationale(this@ArtActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //rationale
            Snackbar.make(view, "Permission for need galery", Snackbar.LENGTH_INDEFINITE).setAction("give permission",
                    View.OnClickListener {
                        //reguest permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

                    }).show()


        } else {
            //reguestPermission
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }*/
            } else {
                // önceden izin verilmişse direkt sormadan galeriye gidiyor.
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }

        }


    }

    private fun handleResponse(placeList:List<Place>){

        val place = placeList[0]// placeList, her seferinde yalnızca tek bir Place nesnesi içerir.
// Bu fonksiyonda position parametresi yoktur, çünkü tek bir Place nesnesiyle çalışıyoruz.
// Her tıklama, veritabanından yeni bir Place nesnesi çeker ve placeList bu yeni nesneyle güncellenir.
// Ancak, placeList hala sadece bir öğe içerdiği için bu öğeye erişmek için placeList[0] kullanılır.
// RecyclerView gibi yapılarda position parametresi, listenin her bir öğesinin sırasını belirtmek için kullanılır.
// Fakat burada tek bir öğe olduğundan, position yerine placeList[0] kullanıyoruz.
        //position parametre
println(place.artName)
        println(place.artistName)
        println(place.year)
        println(place.id)

        binding.artNameid.setText(place.artName)

        // Sanatçının adını kullanıcı arayüzündeki TextView'e set ediyoruz
        binding.artistNameid.setText(place.artistName)
        binding.yearid.setText(place.year.toString())


        // placeList.get(position).artName:
        // Burada 'position' değişkeni geçerli değil, çünkü handleResponse fonksiyonunda
        // herhangi bir 'position' parametresi tanımlı değil. Bu fonksiyon yalnızca belirli
        // bir Place nesnesini almak ve işlemek için kullanılır. Eğer RecyclerView bağlamında
        // çalışıyorsanız, 'position' değişkenini o bağlamda kullanmanız gerekir.
        // RecyclerView'da çoklu öğeleri işlerken, her öğe için position kullanılır.

        // Sanat eserinin yapıldığı yılı kullanıcı arayüzündeki TextView'e set ediyoruz
        // year bir Int tipinde olduğundan, String'e dönüştürmemiz gerekiyor

        // Görsel verisini (ByteArray) alıyoruz
        val byteArray = place.image


        // ByteArray'i Bitmap'e dönüştürüyoruz, böylece görüntüyü ImageView'e set edebiliriz
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.imageView.setImageBitmap(bitmap) // Bitmap'i ImageView'e set ediyoruz
    }
    private fun handleResponse2(place:Place) {
//list olmadan direkt Place döndürdük

        println(place.artName)
        println(place.artistName)
        println(place.year)
        println(place.id)

        binding.artNameid.setText(place.artName)

        // Sanatçının adını kullanıcı arayüzündeki TextView'e set ediyoruz
        binding.artistNameid.setText(place.artistName)
        binding.yearid.setText(place.year.toString())

        // Sanat eserinin yapıldığı yılı kullanıcı arayüzündeki TextView'e set ediyoruz
        // year bir Int tipinde olduğundan, String'e dönüştürmemiz gerekiyor

        // Görsel verisini (ByteArray) alıyoruz
        val byteArray = place.image


        // ByteArray'i Bitmap'e dönüştürüyoruz, böylece görüntüyü ImageView'e set edebiliriz
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.imageView.setImageBitmap(bitmap) // Bitmap'i ImageView'e set ediyoruz
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())

            { result ->//sonuç döndürüyor.
                if (result.resultCode == RESULT_OK) {//resim seçilmişse

                    //val intentFromResult = result.data//bir intent.
                    //val imageData=intentFromResult.data
                    // data özelliği, kullanıcının seçtiği dosya veya resimle ilgili bilgileri taşıyan bir Intent nesnesidir.
                    val imageData = result.data?.data
                    
                    /*    Soru işareti (?.) kullanımı, null güvenliği sağlar ve result.data
                        null olursa işlemi durdurup null döndürerek uygulamanızın çökmesini önler.*/
                    //  if (intentFromResult != null) {
                    //val imageData = intentFromResult?.data//boşsa null fırlatsın diyoruz
                    //   binding.imageView.setImageURI(imageData)
                    if (imageData != null) {
                        try {//sd kart falan çıkar
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(requireContext().contentResolver, imageData)
                                //resmin orjinal verilerine erişim sağlamak ve işlenebilir hale getirmek.
                                // ve null olmasın diyor kesinlikle imageData için


                                selectedBitmap = ImageDecoder.decodeBitmap(source)// Bitmap, bir bilgisayar ekranındaki
                                // piksellerin düzenini ve renklerini saklayan bir veri yapısıdır.
                                // Her bir piksel, bir renk değerini (genellikle RGB veya ARGB formatında) ve konumunu temsil eder.
                                //Resmi dosya formatından çözüp piksellere dönüştürme işlemi Yani.
                                binding.imageView.setImageBitmap(selectedBitmap)

                                //Yani, source nesnesi sadece resmin veri kaynağını temsil ederken, Bitmap nesnesi, bu
                                // verilerin işlenmiş ve görüntülenebilir bir formunu temsil eder. Bu nedenle, resmi işlemek
                                // veya göstermek için Bitmap nesnesine ihtiyaç duyulur.

                            } else {

                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageData)
                                binding.imageView.setImageBitmap(selectedBitmap)

                            }
                        } catch (e: Exception) {
                            println("hata")
                        }


                    }

                }
            }


        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
        {//(ActivityResultContracts.RequestPermission()) izin sorusu olu
            //registerForActivity boolean nesnesi döndürüyor.
                Result ->
            if (Result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            } else {
                //permission denied
                Toast.makeText(requireContext(), "İzin Reddedildi", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun makeSmallerBitmap(image: Bitmap, MaximumSıze: Int): Bitmap {//veritabanına resmi daha  küçük kaydetmek için resmi küçültüyoruz.
        //  return Bitmap.createScaledBitmap(image,100,100,true)
        var width = image.width//resmin genişliğini alıyoruz
        var height = image.height//resmin uzunluğunu alıyoruz.
        val bitmapRatio: Double = width.toDouble() / height.toDouble()//birbirine oranlarını buluyoruz.
        if (bitmapRatio > 1) {//1 den büyük ise geniş bir resim
            width = MaximumSıze// geniş ise geniş uzunluk biz belirliyoruz
            val scaledHeight = width / bitmapRatio//orana göre yeni uzunluğunu hesaplıyoruz
            height = scaledHeight.toInt()//ve onu integer'a çevirip uzunluğa eşitliyoruz
        } else {//değil ise uzun bir resim
            height = MaximumSıze// uzun resim ise verdiğimiz değer yüksekliğe eşit oluyor
            val scaledWidth = height * bitmapRatio//yeni değere göre oran ile çarpıp yeni genişliğimiz buluyoruz
            width = scaledWidth.toInt()// bulduğumuz değeri integere çevirip genişliğe eşitliyoruz.

        }
        return Bitmap.createScaledBitmap(image, width, height, true)

    }

    fun ınsertAll() {
        try {

            if (selectedBitmap != null) {//Resim var mı yok onu kontrol ediyoruz.
                val smallBitmap = makeSmallerBitmap(selectedBitmap!!, MaximumSıze = 300)
                val outputStream = ByteArrayOutputStream()//buffer
                /*Resimler ve Büyük Veriler: Bu tür veriler büyük olduğu için buffer kullanımı gereklidir. Buffer,
            veriyi geçici olarak saklar ve daha sonra toplu halde işlem yaparak bellek kullanımını optimize eder.*/

                smallBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
                /*  Doğrudan Byte Dizisine Yazma İmkânı Olmaması: Sıkıştırma algoritmaları veriyi doğrudan byte
                     dizisine yazmaz, bunun
              yerine bir geçici depolama alanına yazar ve bu alan daha sonra byte dizisine dönüştürülür.
              Bu nedenle, OutputStream, sıkıştırma işlemi sırasında veriyi geçici olarak depolamak ve daha
              sonra kullanılabilir bir formata (byte dizisi) dönüştürmek için gereklidir.*/

                /*
             Rol: smallBitmap bitmap nesnesini PNG formatında sıkıştırır ve sıkıştırılmış verileri
             outputStream nesnesine yazar.
             */
                val byteResim = outputStream.toByteArray()
                val artName = binding.artNameid.text.toString()
                val artistName = binding.artistNameid.text.toString()
                val year = binding.yearid.text.toString().toInt()
                val image = byteResim

                val place = Place(artName, artistName, year, image)
                compositeDisposable.addAll(
                    placeDao.insertAll(place)
                        // Arka plan (I/O) işlemlerini çalıştırmak için RxJava'nın genel I/O iş parçacığı havuzunu kullanır.
// "Schedulers.io()" RxJava'nın Android dışındaki Java veya Kotlin projelerinde de kullanılabilen
// genel bir iş parçacığı yöneticisidir.
                        // Sonuçları Android'in ana iş parçacığında gözlemleyerek UI'yi günceller.
// "AndroidSchedulers.mainThread()" sadece Android platformuna özeldir ve
// kullanıcı arayüzü güncellemelerini Android'in ana iş parçacığında yapmamızı sağlar.
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())

                        .subscribe(this::handleResponse)
                )
            }
        }
        catch (e:Exception){
            Toast.makeText(requireContext(), "Bir Hata Oluştu", Toast.LENGTH_LONG).show()
        }
    }
            private fun handleResponse() {
                Toast.makeText(requireContext(), "Kaydedildi", Toast.LENGTH_LONG).show()
                // 'DetailsFragmentDirections' sınıfı, Safe Args tarafından oluşturulmuş bir sınıftır.
// Bu sınıf, DetailsFragment'tan diğer fragment'lara güvenli ve tip güvenli bir şekilde yönlendirme yapmak için kullanılır.
                val action = DetailsFragmentDirections.actionDetailsFragmentToArtListFragment()
// 'actionDetailsFragmentToArtListFragment()' metodu, DetailsFragment'tan ArtListFragment'a geçişi temsil eden bir NavDirections nesnesi oluşturur.

// 'Navigation' sınıfı, Navigation Component ile çalışmak için kullanılır.
// 'findNavController()' metodu, belirli bir görünümden NavController nesnesini bulur.
// 'binding.root', ViewBinding kullanılarak bağlanmış görünüm hiyerarşisinin kök görünümünü temsil eder.
// Bu satır, geçerli görünüm için NavController'ı bulur.
                Navigation.findNavController(binding.root).navigate(action)
// 'navigate()' metodu, geçiş işlemini gerçekleştirir ve action nesnesini kullanarak DetailsFragment'tan ArtListFragment'a yönlendirir.

            }
        }









