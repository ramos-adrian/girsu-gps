package ar.ramos.girsugps.internal.truck;

import org.springframework.stereotype.Service;

@Service
public class TruckService implements ITruckService{

    private final ITruckRepository truckRepository;

    public TruckService(ITruckRepository truckRepository){
        this.truckRepository = truckRepository;
    }

    @Override
    public Truck findById(Long id) {
        return truckRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Truck not found with id: " + id));
    }

    @Override
    public Truck save(Truck truck) {
        return truckRepository.save(truck);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}
