package com.tayyipgunay.navigationart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Place") // Bu anotasyon, sınıfı Room veritabanında 'Place' adlı bir tablo olarak tanımlar. `tableName` ile tablo adı belirtebiliriz.
class Place(
    @ColumnInfo(name = "artname") // Bu anotasyon, veritabanında bu alanın 'artname' adlı bir sütunda tutulacağını belirtir.
    @SerializedName("artname") // JSON verisinde 'artname' olarak geçen alanı bu değişkenle eşleştirir.
    var artName: String, // Sanat eserinin adını temsil eder.

    @ColumnInfo(name = "artistname") // Bu anotasyon, veritabanında bu alanın 'artistname' adlı bir sütunda tutulacağını belirtir.
    @SerializedName("artistname") // JSON verisinde 'artistname' olarak geçen alanı bu değişkenle eşleştirir.
    var artistName: String, // Sanatçının adını temsil eder.

    @ColumnInfo(name = "year") // Bu anotasyon, veritabanında bu alanın 'year' adlı bir sütunda tutulacağını belirtir.
    @SerializedName("year") // JSON verisinde 'year' olarak geçen alanı bu değişkenle eşleştirir.
    var year: Int, // Sanat eserinin yapıldığı yılı temsil eder.

    @ColumnInfo(name = "image") // Bu anotasyon, veritabanında bu alanın 'image' adlı bir sütunda tutulacağını belirtir.
    @SerializedName("image") // JSON verisinde 'image' olarak geçen alanı bu değişkenle eşleştirir.
    var image: ByteArray, // Sanat eserinin görselini temsil eder. Görsel, `ByteArray` formatında tutulur.
)
{
    @PrimaryKey(autoGenerate = true) // Bu anotasyon, bu alanı tablonun birincil anahtarı olarak tanımlar ve `autoGenerate` ile otomatik olarak artmasını sağlar.
    var id = 0 // Tabloya eklenecek her yeni kayıt için benzersiz bir ID atanır.
}

