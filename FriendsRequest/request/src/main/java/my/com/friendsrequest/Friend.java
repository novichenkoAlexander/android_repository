package my.com.friendsrequest;

import android.content.SearchRecentSuggestionsProvider;
import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable{

    private String name;
    private String surname;
    private long id;

    public Friend(String name, String surname, long id){
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    private Friend(Parcel parcel){
        name = parcel.readString();
        surname = parcel.readString();
        id = parcel.readLong();
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String toString(){
        return name + " " + surname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeLong(id);
    }
}
