package com.jld.userservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jld.userservice.entity.User;
import com.jld.userservice.feignclients.BikeFeignClient;
import com.jld.userservice.feignclients.CarFeignClient;
import com.jld.userservice.models.Bike;
import com.jld.userservice.models.Car;
import com.jld.userservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CarFeignClient carFeignClient;
	
	@Autowired
	BikeFeignClient bikeFeignClient;
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User save(User user) {
		User userNew = userRepository.save(user);
		return userNew;
	}
	
	public List<Car> getCars(int userId) {
		List<Car> cars = restTemplate.getForObject("http://car-service/car/byuser/" + userId, List.class);
		return cars;
	}
	
	public List<Bike> getBikes(int userId) {
		List<Bike> bikes = restTemplate.getForObject("http://bike-service/bike/byuser/" + userId, List.class);
		return bikes;
	}
	
	public Car saveCar(int userId, Car car) {
		car.setUserId(userId);
		Car carNew = carFeignClient.save(car);
		return carNew;
	}
	
	public Bike saveBike(int userId, Bike bike) {
		bike.setUserId(userId);
		Bike newBike = bikeFeignClient.save(bike);
		return newBike;
	}
	
	public Map<String, Object> getUserAndVehicules(int userId) {
		Map<String, Object> result = new HashMap<>();
		User user = userRepository.findById(userId).orElse(null);
		if(user == null) {
			result.put("Mensaje", "No existe el usuario");
			return result;
		}
		result.put("User", user);
		List<Car> cars = carFeignClient.getCars(userId);
		if(cars.isEmpty())
			result.put("Cars", "Ese user no tiene coches");
		else
			result.put("Cars", cars);
		List<Bike> bikes = bikeFeignClient.getBikes(userId);
		if(bikes.isEmpty())
			result.put("Bike", "Ese user no tiene coches");
		else
			result.put("Bike", bikes);
		
		return result;
	}
}
