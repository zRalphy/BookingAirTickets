package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.AirportDtoMapper;
import com.pgs.booking.mappers.CreateUpdateAirportDtoMapper;
import com.pgs.booking.model.dto.AirportDto;
import com.pgs.booking.model.dto.CreateUpdateAirportDto;
import com.pgs.booking.model.entity.Airport;
import com.pgs.booking.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportDtoMapper airportDtoMapper;
    private final CreateUpdateAirportDtoMapper createUpdateAirportDtoMapper;

    public List<AirportDto> getAirports() {
        List<Airport> allAirports = airportRepository.findAll();
        return airportDtoMapper.mapToAirportsDto(allAirports);
    }

    public AirportDto getAirportByCode(String code) {
        Airport airport = airportRepository.findAirportByCode(code).orElseThrow(() -> new ResourceNotFoundException("Airport with id " + code + " not found."));
        return airportDtoMapper.mapToAirportDto(airport);
    }

    public AirportDto getAirportById(long id) {
        Airport airport = airportRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport with id " + id + " not found."));
        return airportDtoMapper.mapToAirportDto(airport);
    }

    public AirportDto addAirport(CreateUpdateAirportDto createUpdateAirportDto) {
        Airport airportToSave = airportRepository.save(createUpdateAirportDtoMapper.mapToAirport(createUpdateAirportDto));
        return airportDtoMapper.mapToAirportDto(airportToSave);
    }

    public AirportDto editAirport(long id, CreateUpdateAirportDto createUpdateAirportDto) {
        Airport airportToEdit = airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport with id " + id + " not found."));

        airportToEdit.setCode(createUpdateAirportDto.getCode());
        airportToEdit.setName(createUpdateAirportDto.getName());
        airportToEdit.setCountry(createUpdateAirportDto.getCountry());
        airportToEdit.setCity(createUpdateAirportDto.getCity());
        Airport airportToSave = airportRepository.save(airportToEdit);
        return airportDtoMapper.mapToAirportDto(airportToSave);
    }
}
