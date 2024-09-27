package ar.ramos.girsugps.internal.truck;

public interface ITruckService {
    Truck findById(Long id);
    Truck save(Truck truck);
}
