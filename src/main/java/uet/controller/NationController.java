package uet.controller;

import uet.DTO.ContinentDTO;
import uet.DTO.NationDTO;
import uet.model.Continent;
import uet.model.Nation;
import uet.service.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nhkha on 25/03/2017.
 */
@RestController
public class NationController {
    private final
    NationService nationService;

    @Autowired
    public NationController(NationService nationService) {
        this.nationService = nationService;
    }

    //create continent
    @RequestMapping(value = "/continent/create", method = RequestMethod.POST)
    public Continent createContinent(@RequestBody ContinentDTO continentDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return nationService.createContinent(continentDTO, token);
    }

    //edit continent
    @RequestMapping(value = "continent/edit", method = RequestMethod.PUT)
    public void editContinent(@RequestBody ContinentDTO continentDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        nationService.editContinent(continentDTO, token);
    }

    //delete continent
    @RequestMapping(value = "/continent/{continentId}/delete", method = RequestMethod.DELETE)
    public void deleteContinent(@PathVariable("continentId") int continentId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        nationService.deleteContinent(continentId, token);
    }

    //create nation
    @RequestMapping(value = "/continent/{continentId}/nation/create", method = RequestMethod.POST)
    public Nation createNation(@RequestBody NationDTO nationDTO, @PathVariable("continentId") int continentId,
                             HttpServletRequest request){
        String token = request.getHeader("auth-token");
        return nationService.createNation(nationDTO, continentId, token);
    }

    //edit nation
    @RequestMapping(value = "nation/edit", method = RequestMethod.PUT)
    public void editNation(@RequestBody NationDTO nationDTO, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        nationService.editNation(nationDTO, token);
    }

    //show all continent
    @RequestMapping(value = "/continent", method = RequestMethod.GET)
    public List<Continent> getAllContinent(){
        return nationService.getAllContinent();
    }

    //show all nation of continent
    // continent/{continentId}

    //delele nation
    @RequestMapping(value = "/nation/{nationId}/delete", method = RequestMethod.DELETE)
    public void deleteNation(@PathVariable("nationId") int nationId, HttpServletRequest request){
        String token = request.getHeader("auth-token");
        nationService.deleteNation(nationId, token);
    }

    //get all nation
    @RequestMapping(value = "/nation", method = RequestMethod.GET)
    public List<Nation> getAllNation(){
        return nationService.getAllNation();
    }
}
