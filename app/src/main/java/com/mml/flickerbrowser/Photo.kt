package com.mml.flickerbrowser

import android.os.Parcel
import android.os.Parcelable


class Photo(
    var title: String?, var author: String?,
    var authorId: String?, var link: String?, var tags: String?, var image: String?
) :  Parcelable {

    companion object CREATOR : Parcelable.Creator<Photo> {
        private const val TAG = "Photo"
        private const val serialVersionUID: Long = 1L

        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorId='$authorId', link='$link', tags='$tags', image='$image')"
    }
//
//    private void writeObject(java.io.ObjectinputputStream input)
//    throws IOException
//    private void readObject(java.io.ObjectInputStream in)
//    throws IOException, ClassNotFoundException;
//    private void readObjectNoData()
//    throws ObjectStreamException;

//    @Throws(IOException::class)
//    private fun writeObject(input: java.io.ObjectOutputStream) {
//        Log.d(TAG, "writeObject: called")
//        input.writeUTF(title)
//        input.writeUTF(author)
//        input.writeUTF(authorId)
//        input.writeUTF(link)
//        input.writeUTF(tags)
//        input.writeUTF(image)
//    }
//
//    @Throws(IOException::class)
//    private fun readObject(input: java.io.ObjectInputStream) {
//        Log.d(TAG, "writeObject: called")
//        title = input.readUTF()
//        author = input.readUTF()
//        authorId = input.readUTF()
//        link = input.readUTF()
//        tags = input.readUTF()
//        image = input.readUTF()
//
//    }
//
//    @Throws(IOException::class)
//    private fun readObjectNoData(){
//        Log.d(TAG, "readObjectNoData: called")
//
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(authorId)
        parcel.writeString(link)
        parcel.writeString(tags)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }


}