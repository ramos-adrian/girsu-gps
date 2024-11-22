package ar.ramos.girsugps.internal.user;


import ar.ramos.girsugps.internal.place.PlaceService;
import com.google.maps.model.LatLng;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PlaceService placeService;

    public UserController(UserService userService, PlaceService placeService) {
        this.userService = userService;
        this.placeService = placeService;
    }

    @RequestMapping("/sethome")
    public ResponseEntity<UserHome> setHome(@RequestBody UserHome.UserHomeDTO userHomeDTO) {
        LatLng latLng = new LatLng(userHomeDTO.getLatitude(), userHomeDTO.getLongitude());
        String placeId = placeService.getPlaceIdFromLatLng(latLng);

        UserHome userHome = new UserHome();
        userHome.setId(null);
        userHome.setLatitude(userHomeDTO.getLatitude());
        userHome.setLongitude(userHomeDTO.getLongitude());
        userHome.setPlaceId(placeId);
        UserHome savedHome = userService.setHome(userHomeDTO.getUsername(), userHome);

        return ResponseEntity.ok(savedHome);
    }

}
