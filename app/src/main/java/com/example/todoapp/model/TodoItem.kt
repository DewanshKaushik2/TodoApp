package com.example.todoapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemtable")
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var itemName: String = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoItem> {
        override fun createFromParcel(parcel: Parcel): TodoItem {
            return TodoItem(parcel)
        }

        override fun newArray(size: Int): Array<TodoItem?> {
            return arrayOfNulls(size)
        }
    }
}