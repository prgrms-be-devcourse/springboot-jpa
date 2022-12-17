package com.example.mission3.repository;

import com.example.mission3.domain.Drink;
import com.example.mission3.domain.Food;
import com.example.mission3.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Integer> {

    @Query("SELECT i FROM Item AS i WHERE type(i) in (Food)")
    List<Food> findAllFoodItems();

    @Query("SELECT i FROM Item AS i WHERE type(i) in (Drink)")
    List<Drink> findAllDrinkItems();
}
