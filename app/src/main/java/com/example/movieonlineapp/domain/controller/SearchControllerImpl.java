package com.example.movieonlineapp.domain.controller;

import com.example.movieonlineapp.domain.model.Movie;

import java.text.Normalizer;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchControllerImpl implements SearchController {
    private static SearchControllerImpl instance;

    public static SearchControllerImpl getInstance(){
        if(instance == null){
            synchronized (SearchControllerImpl.class){
                if(instance == null){
                    instance = new SearchControllerImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Movie> getMovieSearchByName(List<Movie> list, String query) {
        String normalizedQuery = removeVietnameseAccents(query.toLowerCase());
        List<Movie> result = new ArrayList<>();
        for (Movie movie : list) {
            String normalizedTitle = removeVietnameseAccents(movie.getTitle().toLowerCase());
            if (isMatch(normalizedTitle, normalizedQuery)) {
                result.add(movie);
            }
        }
        return result;
    }

    // Hàm loại bỏ dấu tiếng Việt
    private String removeVietnameseAccents(String input) {
        String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
        temp = temp.replaceAll("[^\\p{ASCII}]", "");
        return temp;
    }

    private boolean isMatch(String firstName, String key) {
        String pattern = ".*" + key + ".*";
        Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(firstName);
        return matcher.matches();
    }
}
