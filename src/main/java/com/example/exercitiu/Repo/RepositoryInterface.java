package com.example.exercitiu.Repo;

import com.example.exercitiu.Model.BaseEntity;

import java.util.ArrayList;

public interface RepositoryInterface {
    public void insert(BaseEntity entity);
    public void delete(BaseEntity entity);
    public ArrayList<BaseEntity> getAll();
    public int getLength();
}
