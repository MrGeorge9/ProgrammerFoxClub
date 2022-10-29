package com.example.programmerfoxclub.controllers;

import com.example.programmerfoxclub.models.Drink;
import com.example.programmerfoxclub.models.Food;
import com.example.programmerfoxclub.models.Tricks;
import com.example.programmerfoxclub.models.User;
import com.example.programmerfoxclub.repositories.UserRepository;
import com.example.programmerfoxclub.services.FoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private UserRepository userRepository;
    private FoxService foxService;

    @Autowired
    public MainController(UserRepository userRepository, FoxService foxService) {
        this.userRepository = userRepository;
        this.foxService = foxService;
    }

    @GetMapping("/")
    public String mainPage (Model model, @RequestParam(name = "name", defaultValue = "A9_5Tre1Q") String name){
        if (name.equals("A9_5Tre1Q")) {
            return "redirect:/login";
        } else {
            if (foxService.nameInDatabase(name)) {
                if (foxService.get5LatestActions(name).size()<1 || foxService.selectRightFox(name).getTricks()==null) {
                    model.addAttribute("rightFox", foxService.selectRightFox(name));
                    model.addAttribute("noActionHistory", 0);
                } else {
                    model.addAttribute("rightFox", foxService.selectRightFox(name));
                    model.addAttribute("pictures", foxService.choosePicture(foxService.selectRightFox(name).getTricks(), name));
                    model.addAttribute("latestActions", foxService.get5LatestActions(name));
                }
                return "index";
            } else return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String register (){
        return "register";
    }

    @PostMapping("/register")
    public String register (Model model,@RequestParam(name = "name") String name,
                                        @RequestParam(name = "password") String password,
                                        @RequestParam(name = "passwordAgain") String passwordAgain){
        if (!password.equals(passwordAgain)) {
            model.addAttribute("error", 0);
            return "register";
        } else {
            userRepository.save(new User(name, password));
            foxService.addFox(name);
            model.addAttribute("allFoxes", foxService.getFoxes());
            return "/login";
        }
    }

    @GetMapping("/login")
    public String login (Model model){
        if (foxService.getFoxes().size()>0){
            model.addAttribute("allFoxes", foxService.getFoxes());
        } else {
            model.addAttribute("allFoxes", 0);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login (Model model,@RequestParam(name = "name") String name, @RequestParam(name = "password") String password){
        if (foxService.nameInDatabase(name) && userRepository.findAll().stream().filter(user -> user.getUserName().
                equals(name)).collect(Collectors.toList()).get(0).getPassword().equals(password)) {
            return "redirect:/?name="+name;
        } else {
            model.addAttribute("wrongLogin",0);
            model.addAttribute("allFoxes", foxService.getFoxes());
            return "/login";
        }
    }


    @GetMapping("/nutritionStore")
    public String nutritionStore (Model model,@RequestParam(name = "name") String name){
        model.addAttribute("rightFox", foxService.selectRightFox(name));
        model.addAttribute("foods", Arrays.asList(Food.values()));
        model.addAttribute("drinks", Arrays.asList(Drink.values()));
        return "nutritionStore";
    }

    @PostMapping("/nutritionStore")
    public String nutritionStore (Model model,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "food") Food food,
                                  @RequestParam(name = "drink") Drink drink){
        foxService.updateFox(name,food,drink);
        return "redirect:/?name="+name;
    }

   @GetMapping("/trickCenter")
    public String trickCenter (Model model, @RequestParam(name = "name") String name, Tricks tricks){
        model.addAttribute("rightFox", foxService.selectRightFox(name));
        model.addAttribute("tricks", foxService.availableTricks(name, tricks));
        return "trickCenter";
    }

    @PostMapping("/trickCenter")
    public String trickCenter (Model model,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "trick") String trick){
        foxService.addTrick(name, trick);
        return "redirect:/?name="+name;
    }

    @GetMapping("/actionHistory")
    public String actionHistory (Model model, @RequestParam(name = "name") String name){
        if (foxService.getActionHistory(name).size()>0){
            model.addAttribute("rightFox", foxService.selectRightFox(name));
            model.addAttribute("actionHistory", foxService.getActionHistory(name));
        } else {
            model.addAttribute("rightFox", foxService.selectRightFox(name));
            model.addAttribute("noActionHistory", 0);
        }
        return "actionHistory";
    }

    @GetMapping("/doATrick")
    public String doATrick (Model model, @RequestParam(name = "name") String name){
        if (foxService.selectRightFox(name).getTricks()==null){
            model.addAttribute("rightFox", foxService.selectRightFox(name));
            model.addAttribute("noTricks", 0);
        } else {
            if (foxService.selectRightFox(name).getFoodAmount()==0) {
                model.addAttribute("rightFox", foxService.selectRightFox(name));
                model.addAttribute("HungryFox", 0);
            } else {
                model.addAttribute("rightFox", foxService.selectRightFox(name));
                model.addAttribute("trickOfFox", foxService.selectRightFox(name).getTricks());
            }
        }
        return "doAtrick";
    }

    @PostMapping("/doATrick")
    public String doATrick (@RequestParam(name = "name") String name){
        foxService.selectRightFox(name).setDrinkAmount(foxService.selectRightFox(name).getDrinkAmount()-5);
        foxService.selectRightFox(name).setFoodAmount(foxService.selectRightFox(name).getFoodAmount()-5);
        return "redirect:/?name="+name;
    }

    @GetMapping("/eatAndDrink")
    public String eatAndDrink (@RequestParam(name = "name") String name){
        foxService.selectRightFox(name).setDrinkAmount(foxService.selectRightFox(name).getDrinkAmount()+5);
        foxService.selectRightFox(name).setFoodAmount(foxService.selectRightFox(name).getFoodAmount()+5);
        return "redirect:/?name="+name;
    }
}
