package me.rockintuna.tpiratesdemo.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s order by s.level")
    List<Store> findAllOrderByLevel();
}
