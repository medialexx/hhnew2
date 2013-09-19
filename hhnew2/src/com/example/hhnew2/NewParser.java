package com.example.hhnew2;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

public class NewParser {

	protected InputStream getInputStream() {
		URL feedUrl = null;
		try {
			feedUrl = new URL("http://lenta.ru/rss/last24");
			//feedUrl = new URL("http://anddev.ru/feed");
		} catch (MalformedURLException e1) {
			feedUrl = null;
		}
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			return null;
		}
	}

	public ArrayList<Pars> parse() {
		final Pars currentPost = new Pars();
		final ArrayList<Pars> messages = new ArrayList<Pars>();
		RootElement root = new RootElement("rss");
		Element channel = root.getChild("channel");
		Element item = channel.getChild("item");
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				messages.add(currentPost.copy());
			}
		});
	item.getChild("enclosure").setStartElementListener(new StartElementListener() {
			
			@Override
			public void start(Attributes attributes) {
				// TODO Auto-generated method stub
				currentPost.image=attributes.getValue("url");
			}
		});
		item.getChild("title").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentPost.title = body;
					}
				});               
		item.getChild("link").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentPost.link = body;
					}
				});
		item.getChild("description").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentPost.description = body;
					}
				});
		item.getChild("pubDate").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentPost.setDate(body);
					}
				});
		item.getChild("category").setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentPost.category = body;
					}
				});
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			return null;
		}
		return messages;
	}
}
