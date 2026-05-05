% ============================================
% VEHICLE CLASSIFICATION - Complete PROLOG Program
% ============================================

:- initialization(main).

% ===== FACTS =====

% Vehicle types
vehicle(car).
vehicle(motorcycle).
vehicle(truck).
vehicle(bus).
vehicle(bicycle).
vehicle(train).
vehicle(airplane).
vehicle(helicopter).
vehicle(boat).
vehicle(ship).

% ===== VEHICLE PROPERTIES =====

% Number of wheels
wheels(car, 4).
wheels(motorcycle, 2).
wheels(truck, 6).
wheels(bus, 6).
wheels(bicycle, 2).
wheels(train, 0).    % Trains run on tracks
wheels(airplane, 3). % Landing gear
wheels(helicopter, 3).
wheels(boat, 0).
wheels(ship, 0).

% Engine type
engine_type(car, internal_combustion).
engine_type(motorcycle, internal_combustion).
engine_type(truck, diesel).
engine_type(bus, diesel).
engine_type(bicycle, human).
engine_type(train, electric).
engine_type(airplane, jet).
engine_type(helicopter, turbine).
engine_type(boat, outboard_motor).
engine_type(ship, marine_diesel).

% Fuel type
fuel(car, gasoline).
fuel(motorcycle, gasoline).
fuel(truck, diesel).
fuel(bus, diesel).
fuel(bicycle, none).
fuel(train, electricity).
fuel(airplane, aviation_fuel).
fuel(helicopter, aviation_fuel).
fuel(boat, gasoline).
fuel(ship, diesel).

% Maximum speed (km/h)
max_speed(car, 200).
max_speed(motorcycle, 180).
max_speed(truck, 120).
max_speed(bus, 100).
max_speed(bicycle, 30).
max_speed(train, 150).
max_speed(airplane, 900).
max_speed(helicopter, 300).
max_speed(boat, 80).
max_speed(ship, 50).

% Passenger capacity
passengers(car, 5).
passengers(motorcycle, 2).
passengers(truck, 3).
passengers(bus, 50).
passengers(bicycle, 1).
passengers(train, 200).
passengers(airplane, 150).
passengers(helicopter, 6).
passengers(boat, 10).
passengers(ship, 500).

% Terrain/Environment
environment(car, land).
environment(motorcycle, land).
environment(truck, land).
environment(bus, land).
environment(bicycle, land).
environment(train, railway).
environment(airplane, air).
environment(helicopter, air).
environment(boat, water).
environment(ship, water).

% Weight category (tons)
weight_category(car, light).
weight_category(motorcycle, light).
weight_category(truck, heavy).
weight_category(bus, heavy).
weight_category(bicycle, light).
weight_category(train, very_heavy).
weight_category(airplane, heavy).
weight_category(helicopter, medium).
weight_category(boat, medium).
weight_category(ship, very_heavy).

% Purpose
purpose(car, personal_transport).
purpose(motorcycle, personal_transport).
purpose(truck, cargo).
purpose(bus, public_transport).
purpose(bicycle, recreation).
purpose(train, public_transport).
purpose(airplane, travel).
purpose(helicopter, special_purpose).
purpose(boat, recreation).
purpose(ship, cargo).

% ===== CLASSIFICATION RULES =====

% Land vehicles
land_vehicle(V) :-
    vehicle(V),
    environment(V, land).

% Water vehicles
water_vehicle(V) :-
    environment(V, water).

% Air vehicles
air_vehicle(V) :-
    environment(V, air).

% Railway vehicles
railway_vehicle(V) :-
    environment(V, railway).

% Motorized vehicles (have engine, not human-powered)
motorized(V) :-
    vehicle(V),
    engine_type(V, Engine),
    Engine \= human.

% Human-powered vehicles
human_powered(V) :-
    engine_type(V, human).

% Electric vehicles
electric_vehicle(V) :-
    fuel(V, electricity).

% Diesel vehicles
diesel_vehicle(V) :-
    fuel(V, diesel).

% Fast vehicles (over 150 km/h)
fast_vehicle(V) :-
    max_speed(V, Speed),
    Speed > 150.

% Slow vehicles (under 50 km/h)
slow_vehicle(V) :-
    max_speed(V, Speed),
    Speed < 50.

% Heavy vehicles
heavy_vehicle(V) :-
    weight_category(V, heavy).

% Very heavy vehicles
very_heavy_vehicle(V) :-
    weight_category(V, very_heavy).

% Public transport vehicles
public_transport(V) :-
    purpose(V, public_transport).

% Cargo vehicles
cargo_vehicle(V) :-
    purpose(V, cargo).

% Personal transport vehicles
personal_vehicle(V) :-
    purpose(V, personal_transport).

% Small capacity (less than 5 passengers)
small_vehicle(V) :-
    passengers(V, Cap),
    Cap < 5.

% Large capacity (over 50 passengers)
large_vehicle(V) :-
    passengers(V, Cap),
    Cap > 50.

% ===== QUERY FUNCTIONS =====

list_land_vehicles :-
    findall(V, land_vehicle(V), List),
    write('Land vehicles: '), write(List), nl.

list_water_vehicles :-
    findall(V, water_vehicle(V), List),
    write('Water vehicles: '), write(List), nl.

list_air_vehicles :-
    findall(V, air_vehicle(V), List),
    write('Air vehicles: '), write(List), nl.

list_motorized_vehicles :-
    findall(V, motorized(V), List),
    write('Motorized vehicles: '), write(List), nl.

list_electric_vehicles :-
    findall(V, electric_vehicle(V), List),
    write('Electric vehicles: '), write(List), nl.

list_fast_vehicles :-
    findall(V, fast_vehicle(V), List),
    write('Fast vehicles (>150 km/h): '), write(List), nl.

list_heavy_vehicles :-
    findall(V, heavy_vehicle(V), List),
    write('Heavy vehicles: '), write(List), nl.

list_public_transport :-
    findall(V, public_transport(V), List),
    write('Public transport vehicles: '), write(List), nl.

% ===== DESCRIBE VEHICLE =====

describe_vehicle(V) :-
    vehicle(V),
    format('~n=== ~w ===~n', [V]),
    wheels(V, W), format('  Wheels: ~w~n', [W]),
    engine_type(V, E), format('  Engine: ~w~n', [E]),
    fuel(V, F), format('  Fuel: ~w~n', [F]),
    max_speed(V, S), format('  Max speed: ~w km/h~n', [S]),
    passengers(V, P), format('  Passenger capacity: ~w~n', [P]),
    environment(V, Env), format('  Environment: ~w~n', [Env]),
    weight_category(V, Wt), format('  Weight: ~w~n', [Wt]),
    purpose(V, Pur), format('  Purpose: ~w~n', [Pur]).

% ===== IDENTIFICATION RULES =====

% Identify vehicle by characteristics
identify_vehicle(Wheels, Engine, FuelType, Environment, PassCapacity, Vehicle) :-
    vehicle(Vehicle),
    wheels(Vehicle, Wheels),
    engine_type(Vehicle, Engine),
    fuel(Vehicle, FuelType),
    environment(Vehicle, Environment),
    passengers(Vehicle, PassCapacity).

% What vehicle has 2 wheels, human engine, and is for recreation?
recreational_bike(V) :-
    wheels(V, 2),
    engine_type(V, human),
    purpose(V, recreation).

% ===== MAIN PROGRAM =====

main :-
    nl, write('============================================'), nl,
    write('      VEHICLE CLASSIFICATION SYSTEM'), nl,
    write('============================================'), nl, nl,
    
    write('=== WELCOME TO THE VEHICLE CLASSIFIER ==='), nl, nl,
    
    % List all vehicles
    write('=== ALL VEHICLES IN DATABASE ==='), nl,
    forall(vehicle(V), format('  ~w~n', [V])), nl,
    
    % Describe each vehicle
    write('=== VEHICLE DESCRIPTIONS ==='), nl,
    forall(vehicle(V), describe_vehicle(V)), nl,
    
    % Classifications
    write('=== CLASSIFICATIONS ==='), nl, nl,
    
    list_land_vehicles, nl,
    list_water_vehicles, nl,
    list_air_vehicles, nl,
    list_motorized_vehicles, nl,
    list_electric_vehicles, nl,
    list_fast_vehicles, nl,
    list_heavy_vehicles, nl,
    list_public_transport, nl,
    
    % Expert system queries
    write('=== EXPERT SYSTEM QUERIES ==='), nl,
    
    % Question 1: Which vehicles have 4 wheels?
    write('Q: Which vehicles have 4 wheels?'), nl,
    findall(V, wheels(V, 4), FourWheelers),
    format('A: ~w~n', [FourWheelers]), nl,
    
    % Question 2: Which vehicles run on diesel?
    write('Q: Which vehicles run on diesel?'), nl,
    findall(V, diesel_vehicle(V), DieselVehicles),
    format('A: ~w~n', [DieselVehicles]), nl,
    
    % Question 3: Which vehicles can go over 200 km/h?
    write('Q: Which vehicles can go over 200 km/h?'), nl,
    findall(V, (max_speed(V, S), S > 200), SuperFast),
    format('A: ~w~n', [SuperFast]), nl,
    
    % Question 4: Which vehicles carry cargo?
    write('Q: Which vehicles carry cargo?'), nl,
    findall(V, cargo_vehicle(V), CargoVehicles),
    format('A: ~w~n', [CargoVehicles]), nl,
    
    % Question 5: Which vehicles are human-powered?
    write('Q: Which vehicles are human-powered?'), nl,
    findall(V, human_powered(V), HumanPowered),
    format('A: ~w~n', [HumanPowered]), nl,
    
    % Question 6: Which vehicles can carry more than 100 people?
    write('Q: Which vehicles can carry more than 100 people?'), nl,
    findall(V, (passengers(V, P), P > 100), LargeCapacity),
    format('A: ~w~n', [LargeCapacity]), nl,
    
    % Question 7: Which vehicles have 0 wheels?
    write('Q: Which vehicles have 0 wheels?'), nl,
    findall(V, wheels(V, 0), ZeroWheels),
    format('A: ~w~n', [ZeroWheels]), nl,
    
    % Question 8: Which vehicles are used for personal transport?
    write('Q: Which vehicles are used for personal transport?'), nl,
    findall(V, personal_vehicle(V), Personal),
    format('A: ~w~n', [Personal]), nl,
    
    % Question 9: Find bicycle by description
    write('Q: Which vehicle has 2 wheels, human engine, and is for recreation?'), nl,
    findall(V, recreational_bike(V), Recreational),
    format('A: ~w~n', [Recreational]), nl,
    
    % Question 10: Which vehicles are very heavy?
    write('Q: Which vehicles are very heavy?'), nl,
    findall(V, very_heavy_vehicle(V), VeryHeavy),
    format('A: ~w~n', [VeryHeavy]), nl,
    
    write('============================================'), nl,
    write('Vehicle Classification System Completed!'), nl,
    write('============================================'), nl,
    
    halt.