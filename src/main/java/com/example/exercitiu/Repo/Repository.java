package com.example.exercitiu.Repo;

import com.example.exercitiu.Model.BaseEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class Repository implements RepositoryInterface {
    private ArrayList<BaseEntity> objects = new ArrayList<>();

    @Override
    public void insert(BaseEntity entity) {
        objects.add(entity);
    }

    @Override
    public void delete(BaseEntity entity) {
        objects.remove(entity);
    }

    @Override
    public ArrayList<BaseEntity> getAll() {
        return objects;
    }

    @Override
    public int getLength() {
        return objects.size();
    }

}
