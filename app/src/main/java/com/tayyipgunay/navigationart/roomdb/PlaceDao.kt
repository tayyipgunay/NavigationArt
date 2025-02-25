package com.tayyipgunay.navigationart.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import com.tayyipgunay.navigationart.model.Place
//import io.reactivex.Completable
//import io.reactivex.Flowable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface PlaceDao {

    @Query("SELECT * FROM Place")
    suspend fun getAll(): List<Place>// Place listesini asenkron çeker, ancak sürekli güncellemez.

    @Query("SELECT * FROM Place WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: Int): Flowable<Place>//
    // Tek bir Place nesnesi döndürecekse List gerekmez.



    @Delete
    fun   delete(place: Place) : Completable

    @Insert
    fun   insertAll(vararg places: Place):Completable


// Birden fazla Place nesnesi döndürecekse List kullanılır.
// fun loadAllByIds(userIds: Int): Flowable<List<Place>>

    /*fun getAll(): Flowable<List<Place>>
       // Place listesini sürekli izler ve otomatik günceller.
        RxJava'da veri akışlarını gözlemlemek için kullanılır.
        */

    /*suspend fun getAll() :Place
     Liste (List<T>): Birden fazla kaydı döndürmek için kullanılır.
 Tek Nesne (T): Yalnızca tek bir kayıt döndürecek sorgular için kullanılır.
*/
    /*
     try-catch: Veritabanı işlemlerinde oluşabilecek hataları (bağlantı sorunları, veri kısıtlamaları gibi) yakalamak için kullanılır. Veritabanı işlemleri, genellikle başarılı olur veya bir istisna (exception) fırlatır.
     Response: API isteklerinde kullanılır. HTTP yanıt kodlarını (örneğin, 404: Bulunamadı, 500: Sunucu hatası) ve başarılı yanıtları işlemek için gereklidir.
 */
}