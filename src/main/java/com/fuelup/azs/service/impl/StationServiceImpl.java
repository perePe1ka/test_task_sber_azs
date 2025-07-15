package com.fuelup.azs.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuelup.azs.dto.FuelDto;
import com.fuelup.azs.dto.StationDto;
import com.fuelup.azs.entity.FuelDispenser;
import com.fuelup.azs.entity.Price;
import com.fuelup.azs.entity.Station;
import com.fuelup.azs.repository.FuelDispenserRepository;
import com.fuelup.azs.repository.PriceRepository;
import com.fuelup.azs.repository.StationRepository;
import com.fuelup.azs.service.ApiService;
import com.fuelup.azs.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StationServiceImpl implements StationService {

    private static final Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final ApiService api;
    private final StationRepository stationRepo;
    private final FuelDispenserRepository dispenserRepo;
    private final PriceRepository priceRepo;
    private final ObjectMapper om;

    public StationServiceImpl() {
        this.api = new ApiService();
        this.stationRepo = new StationRepository();
        this.dispenserRepo = new FuelDispenserRepository();
        this.priceRepo = new PriceRepository();

        this.om = new ObjectMapper()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void loadAndSaveStations() {

        try {
            String body = api.getStationsData();
            List<StationDto> dtos =
                    om.readValue(body, new TypeReference<>() {
                    });

            log.info("Получено {} станций из API", dtos.size());
            
            for (StationDto dto : dtos) {
                try {
                    saveStation(dto);
                } catch (Exception e) {
                    log.error("Ошибка обработки станции {}: {}", dto.getId(), e.getMessage(), e);
                }
            }
            
            log.info("Загрузка станций завершена");

        } catch (IOException ex) {
            throw new RuntimeException("Не удалось получить станции", ex);
        }
    }

    private void saveStation(StationDto dto) {

        Station station = stationRepo
                .findByExternalId(dto.getId())
                .orElseGet(() -> createStation(dto));

        updateStation(station, dto);
        stationRepo.save(station);

        if (dto.getFuels() != null && !dto.getFuels().isEmpty()) {
            dto.getFuels().forEach(f -> upsertDispenser(station, f));
        } else {
            log.warn("Для станции {} нет данных о ТРК", dto.getId());
        }
    }

    private Station createStation(StationDto d) {
        return new Station(
                d.getId(),
                d.getName(),
                d.getAddress(),
                d.getBrand(),
                d.getBrandId(),
                d.getOrganization() == null ? null : d.getOrganization().getName(),
                d.getOrganization() == null ? null : d.getOrganization().getInn(),
                d.getOrganization() == null ? null : d.getOrganization().getKpp(),
                d.getLocation() == null ? null : d.getLocation().getLatitude(),
                d.getLocation() == null ? null : d.getLocation().getLongitude(),
                d.getTakeOffMode(),
                d.getPostPay(),
                d.getEnable()
        );
    }

    private void updateStation(Station s, StationDto d) {
        s.setName(d.getName());
        s.setAddress(d.getAddress());
        s.setBrand(d.getBrand());
        s.setBrandId(d.getBrandId());
        
        if (d.getOrganization() != null) {
            s.setOrganizationName(d.getOrganization().getName());
            s.setOrganizationInn(d.getOrganization().getInn());
            s.setOrganizationKpp(d.getOrganization().getKpp());
        }
        
        if (d.getLocation() != null) {
            s.setLatitude(d.getLocation().getLatitude());
            s.setLongitude(d.getLocation().getLongitude());
        }
        
        s.setTakeOffMode(d.getTakeOffMode());
        s.setPostPay(d.getPostPay());
        s.setEnable(d.getEnable());
    }

    private void upsertDispenser(Station st, FuelDto dto) {
        if (dto.getId() == null || dto.getId().trim().isEmpty()) {
            log.warn("Пропущена ТРК с пустым ID для станции {}", st.getExternalId());
            return;
        }

        Optional<FuelDispenser> opt =
                dispenserRepo.findByExternalIdAndStation(dto.getId(), st);

        FuelDispenser disp = opt.orElseGet(() -> {
            log.debug("Создана новая ТРК: {} для станции: {}", dto.getId(), st.getExternalId());
            return new FuelDispenser(
                    dto.getId(),
                    dto.getId(),
                    dto.getTypeId(),
                    dto.getType(),
                    dto.getBrand(),
                    dto.getName(),
                    dto.getPrice(),
                    st);
        });

        disp.setFuelType(dto.getType());
        disp.setFuelTypeId(dto.getTypeId());
        disp.setFuelBrand(dto.getBrand());
        disp.setFuelName(dto.getName());
        disp.setCurrentPrice(dto.getPrice());

        dispenserRepo.save(disp);

        if (dto.getPrice() != null) {
            try {
                Price price = new Price(dto.getPrice(), disp);
                priceRepo.save(price);
                log.info("Сохранена цена {} для ТРК {} станции {}", 
                        dto.getPrice(), dto.getId(), st.getExternalId());
            } catch (Exception e) {
                log.error("Ошибка сохранения цены {} для ТРК {} станции {}: {}", 
                        dto.getPrice(), dto.getId(), st.getExternalId(), e.getMessage(), e);
            }
        } else {
            log.warn("Цена не указана для ТРК {} станции {}", dto.getId(), st.getExternalId());
        }
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepo.findAll();
    }

}
