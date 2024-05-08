package com.codeWithSrb.BookYourSlot.Repository;

import com.codeWithSrb.BookYourSlot.Model.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingDetails, Integer> {
}
