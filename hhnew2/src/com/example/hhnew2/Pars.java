package com.example.hhnew2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

public class Pars implements Parcelable {
	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"E, dd MMM yyyy HH:mm:ss Z", Locale.US);
	@SuppressLint("SimpleDateFormat")
	static SimpleDateFormat OUT_FORMATTER = new SimpleDateFormat(
			"dd MMMMM yyyy', ' HH:mm");
	public String image;
	public String title;
	public String link;
	public String description;
	public String date;
	public String category;

	public Pars() {
		image="";
		title = "";
		link = "";
		description = "";
		date = "";
		category="";
	}

	public void setDate(String date) {
		try {
			this.date = (String) OUT_FORMATTER.format(FORMATTER.parse(date
					.trim()));
		} catch (ParseException e) {
			this.date = null;
		}
	}

	public Pars copy() {
		Pars copy = new Pars();
		copy.image=image;
		copy.title = title;
		copy.link = link;
		copy.description = description;
		copy.date = date;
		copy.category=category;
		return copy;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(image);
		out.writeString(title);
		out.writeString(link);
		out.writeString(description);
		out.writeString(date);
		out.writeString(category);
	}

	public static final Parcelable.Creator<Pars> CREATOR = new Parcelable.Creator<Pars>() {

		public Pars createFromParcel(Parcel source) {
			return new Pars(source);
		}

		public Pars[] newArray(int size) {
			return new Pars[size];
		}
	};

	private Pars(Parcel source) {
		image=source.readString();
		title = source.readString();
		link = source.readString();
		description = source.readString();
		date = source.readString();
		category=source.readString();
	}
}