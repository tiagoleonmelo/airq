package deti.tqs.airq.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.airq.services.AirCache;

@Repository
public interface AirRepository extends JpaRepository<AirCache, Long>
{



}