% ============================================
% BIRD CLASSIFICATION - Complete PROLOG Program
% ============================================

:- initialization(main).

% ===== FACTS =====

% Bird species
bird(penguin).
bird(ostrich).
bird(eagle).
bird(sparrow).
bird(parrot).
bird(flamingo).
bird(owl).
bird(peacock).
bird(kiwi).
bird(toucan).

% ===== CLASSIFICATION RULES =====

% Flight ability
can_fly(eagle).
can_fly(sparrow).
can_fly(parrot).
can_fly(owl).
can_fly(toucan).
cannot_fly(penguin).
cannot_fly(ostrich).
cannot_fly(kiwi).

% Size categories
size(penguin, medium).
size(ostrich, large).
size(eagle, large).
size(sparrow, small).
size(parrot, medium).
size(flamingo, large).
size(owl, medium).
size(peacock, large).
size(kiwi, small).
size(toucan, medium).

% Color patterns
color(penguin, black_white).
color(ostrich, brown).
color(eagle, brown).
color(sparrow, brown).
color(parrot, colorful).
color(flamingo, pink).
color(owl, brown_grey).
color(peacock, colorful).
color(kiwi, brown).
color(toucan, black_yellow).

% Habitat
habitat(penguin, arctic).
habitat(ostrich, savanna).
habitat(eagle, mountains).
habitat(sparrow, urban).
habitat(parrot, jungle).
habitat(flamingo, wetlands).
habitat(owl, forest).
habitat(peacock, forest).
habitat(kiwi, forest).
habitat(toucan, jungle).

% Diet
diet(penguin, carnivore).
diet(ostrich, herbivore).
diet(eagle, carnivore).
diet(sparrow, omnivore).
diet(parrot, herbivore).
diet(flamingo, carnivore).
diet(owl, carnivore).
diet(peacock, omnivore).
diet(kiwi, omnivore).
diet(toucan, omnivore).

% Distinct features
has_long_neck(flamingo).
has_long_neck(ostrich).
has_tail_feathers(peacock).
has_beak(toucan).
nocturnal(owl).
can_swim(penguin).
can_run_fast(ostrich).

% ===== CLASSIFICATION RULES =====

% Rule 1: Flightless bird
flightless_bird(Bird) :-
    bird(Bird),
    cannot_fly(Bird).

% Rule 2: Bird of prey
bird_of_prey(Bird) :-
    bird(Bird),
    diet(Bird, carnivore),
    can_fly(Bird),
    size(Bird, large).

% Rule 3: Colorful bird
colorful_bird(Bird) :-
    color(Bird, colorful).

% Rule 4: Arctic bird
arctic_bird(Bird) :-
    habitat(Bird, arctic).

% Rule 5: Jungle bird
jungle_bird(Bird) :-
    habitat(Bird, jungle).

% Rule 6: Water bird
water_bird(Bird) :-
    habitat(Bird, wetlands).
water_bird(Bird) :-
    can_swim(Bird).

% Rule 7: Nocturnal bird
nocturnal_bird(Bird) :-
    nocturnal(Bird).

% Rule 8: Songbird
songbird(Bird) :-
    bird(Bird),
    size(Bird, small),
    can_fly(Bird),
    diet(Bird, omnivore).

% Rule 9: Large bird
large_bird(Bird) :-
    size(Bird, large).

% Rule 10: Pet bird
pet_bird(Bird) :-
    bird(Bird),
    color(Bird, colorful),
    can_fly(Bird),
    size(Bird, medium).

% ===== IDENTIFICATION RULES =====

% Identify bird based on characteristics
identify_bird(Flight, Size, Color, HabitatType, DietType, Bird) :-
    bird(Bird),
    (Flight = fly -> can_fly(Bird) ; cannot_fly(Bird)),
    size(Bird, Size),
    color(Bird, Color),
    habitat(Bird, HabitatType),
    diet(Bird, DietType).

% Describe a bird
describe_bird(Bird) :-
    bird(Bird),
    format('~n--- ~w ---~n', [Bird]),
    (can_fly(Bird) -> format('  Can fly: Yes~n') ; format('  Can fly: No~n')),
    size(Bird, Size), format('  Size: ~w~n', [Size]),
    color(Bird, Color), format('  Color: ~w~n', [Color]),
    habitat(Bird, Habitat), format('  Habitat: ~w~n', [Habitat]),
    diet(Bird, Diet), format('  Diet: ~w~n', [Diet]).

% ===== QUERY FUNCTIONS =====

% List all birds by category
list_flightless_birds :-
    findall(B, flightless_bird(B), Birds),
    write('Flightless birds: '), write(Birds), nl.

list_birds_of_prey :-
    findall(B, bird_of_prey(B), Birds),
    write('Birds of prey: '), write(Birds), nl.

list_colorful_birds :-
    findall(B, colorful_bird(B), Birds),
    write('Colorful birds: '), write(Birds), nl.

list_jungle_birds :-
    findall(B, jungle_bird(B), Birds),
    write('Jungle birds: '), write(Birds), nl.

list_water_birds :-
    findall(B, water_bird(B), Birds),
    write('Water birds: '), write(Birds), nl.

list_nocturnal_birds :-
    findall(B, nocturnal_bird(B), Birds),
    write('Nocturnal birds: '), write(Birds), nl.

list_large_birds :-
    findall(B, large_bird(B), Birds),
    write('Large birds: '), write(Birds), nl.

list_songbirds :-
    findall(B, songbird(B), Birds),
    write('Songbirds: '), write(Birds), nl.

list_pet_birds :-
    findall(B, pet_bird(B), Birds),
    write('Popular pet birds: '), write(Birds), nl.

% ===== MAIN PROGRAM =====

main :-
    nl, write('============================================'), nl,
    write('     BIRD CLASSIFICATION SYSTEM'), nl,
    write('============================================'), nl, nl,
    
    write('=== WELCOME TO THE BIRD CLASSIFIER ==='), nl, nl,
    
    % Show all birds
    write('=== ALL BIRDS IN DATABASE ==='), nl,
    forall(bird(B), format('  ~w~n', [B])), nl,
    
    % Descriptions
    write('=== BIRD DESCRIPTIONS ==='), nl,
    forall(bird(B), describe_bird(B)), nl,
    
    % Classifications
    write('=== CLASSIFICATIONS ==='), nl, nl,
    
    list_flightless_birds, nl,
    list_birds_of_prey, nl,
    list_colorful_birds, nl,
    list_jungle_birds, nl,
    list_water_birds, nl,
    list_nocturnal_birds, nl,
    list_large_birds, nl,
    list_songbirds, nl,
    list_pet_birds, nl,
    
    % Expert system queries
    write('=== EXPERT SYSTEM QUERIES ==='), nl,
    
    % Question: Which birds cannot fly?
    write('Q: Which birds cannot fly?'), nl,
    write('A: '), list_flightless_birds, nl,
    
    % Question: Which birds are colorful?
    write('Q: Which birds are colorful?'), nl,
    write('A: '), list_colorful_birds, nl,
    
    % Question: Which birds live in the jungle?
    write('Q: Which birds live in the jungle?'), nl,
    write('A: '), list_jungle_birds, nl,
    
    % Question: Which bird is black and white and lives in the arctic?
    write('Q: Which bird is black/white and lives in the arctic?'), nl,
    findall(B, (bird(B), color(B, black_white), habitat(B, arctic)), ArcticBird),
    format('A: ~w~n', [ArcticBird]), nl,
    
    % Question: Which bird is pink and lives in wetlands?
    write('Q: Which bird is pink and lives in wetlands?'), nl,
    findall(B, (bird(B), color(B, pink), habitat(B, wetlands)), PinkBird),
    format('A: ~w~n', [PinkBird]), nl,
    
    % Question: Which bird is large, can fly, and eats meat?
    write('Q: Which bird is large, can fly, and eats meat?'), nl,
    findall(B, (bird(B), size(B, large), can_fly(B), diet(B, carnivore)), LargeCarnivore),
    format('A: ~w~n', [LargeCarnivore]), nl,
    
    % Question: Which bird has a long neck?
    write('Q: Which birds have long necks?'), nl,
    findall(B, has_long_neck(B), LongNeckBirds),
    format('A: ~w~n', [LongNeckBirds]), nl,
    
    % Question: Which bird is active at night?
    write('Q: Which bird is active at night?'), nl,
    findall(B, nocturnal(B), NocturnalBirds),
    format('A: ~w~n', [NocturnalBirds]), nl,
    
    write('============================================'), nl,
    write('Bird Classification System Completed!'), nl,
    write('============================================'), nl,
    
    halt.

% ===== ADDITIONAL QUERIES FOR INTERACTIVE USE =====
% To use interactively in SWI-Prolog, load this file and type:
% 
% ?- bird(X).                    % List all birds
% ?- can_fly(eagle).             % Check if eagle can fly
% ?- flightless_bird(X).         % Find flightless birds
% ?- bird_of_prey(X).            % Find birds of prey
% ?- describe_bird(penguin).     % Describe a specific bird
% ?- colorful_bird(X).           % Find colorful birds
% ?- identify_bird(fly, large, brown, mountains, carnivore, X).  % Identify by features