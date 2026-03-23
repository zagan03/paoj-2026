package com.pao.laboratory05.playlist;
import java.util.Arrays;
public class Playlist {
    private String name;
    private Song[] songs;
    public Playlist(String name) {
        this.name = name;
        this.songs = new Song[0];
    }
    public void addSong(Song song) {
        Song[] newArray = new Song[songs.length + 1];
        System.arraycopy(songs, 0, newArray, 0, songs.length);
        newArray[songs.length] = song;
        this.songs = newArray; // cat timp exista o referinta catre newArray, el traieste pe heap
    }
    public void printSortedByTitle() {
        Song[] clona = songs.clone();
        Arrays.sort(clona);
        for (Song s : clona) {
            System.out.println(s);
        }
    }
    public void printSortedByDuration() {
        Song[] clona = songs.clone();
        Arrays.sort(clona, new SongDurationComparator());
        for (Song s : clona) {
            System.out.println(s);
        }
    }
    public int getTotalDuration() {
        int sum = 0;
        for(Song s : songs) {
            sum += s.durationSeconds();
        }
        return sum;
    }
    public String getName() {
        return name;
    }
}
