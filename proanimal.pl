% ============================================
% SIMPLE ANIMAL IDENTIFICATION EXPERT SYSTEM
% ============================================

:- initialization(main).

% ===== FACTS =====

% animal(Name, Class, Diet, Features, Habitat)
animal(cheetah, mammal, carnivore, [spots, fast], land).
animal(tiger, mammal, carnivore, [stripes], land).
animal(giraffe, mammal, herbivore, [long_neck], land).
animal(zebra, mammal, herbivore, [stripes], land).
animal(ostrich, bird, herbivore, [long_neck, flightless], land).
animal(penguin, bird, carnivore, [flightless, swims], cold).
animal(dolphin, mammal, carnivore, [swims, intelligent], water).
animal(shark, fish, carnivore, [swims], water).
animal(eagle, bird, carnivore, [flies], air).
animal(bat, mammal, carnivore, [flies, nocturnal], air).

% ===== RULES =====

% Identify by characteristics
identify(Animal) :-
    animal(Animal, Class, Diet, Features, Habitat),
    format('~n=== IDENTIFIED: ~w ===~n', [Animal]),
    format('Class: ~w~n', [Class]),
    format('Diet: ~w~n', [Diet]),
    format('Features: ~w~n', [Features]),
    format('Habitat: ~w~n', [Habitat]).

% Find animals by class
mammals(X) :- animal(X, mammal, _, _, _).
birds(X) :- animal(X, bird, _, _, _).
fish(X) :- animal(X, fish, _, _, _).

carnivores(X) :- animal(X, _, carnivore, _, _).
herbivores(X) :- animal(X, _, herbivore, _, _).

% Find animals that can fly
flies(X) :- animal(X, _, _, Features, _), member(flies, Features).

% Find animals that swim
swims(X) :- animal(X, _, _, Features, _), member(swims, Features).

% ===== MAIN =====

main :-
    nl, write('=== ANIMAL IDENTIFICATION EXPERT SYSTEM ==='), nl, nl,
    
    write('All animals in database:'), nl,
    forall(animal(A,_,_,_,_), format('  ~w~n', [A])), nl,
    
    write('Mammals: '), findall(X, mammals(X), M), write(M), nl,
    write('Birds: '), findall(X, birds(X), B), write(B), nl,
    write('Fish: '), findall(X, fish(X), F), write(F), nl, nl,
    
    write('Carnivores: '), findall(X, carnivores(X), C), write(C), nl,
    write('Herbivores: '), findall(X, herbivores(X), H), write(H), nl, nl,
    
    write('Animals that can fly: '), findall(X, flies(X), Fly), write(Fly), nl,
    write('Animals that can swim: '), findall(X, swims(X), Swim), write(Swim), nl, nl,
    
    % Identify specific animals
    write('=== IDENTIFICATION EXAMPLES ==='), nl,
    identify(cheetah),
    identify(penguin),
    identify(eagle),
    
    % Query: What animal has stripes and is a mammal?
    write('~n=== QUERY ==='), nl,
    write('Which mammal has stripes? '),
    findall(X, (mammals(X), animal(X,_,_,Features,_), member(stripes, Features)), Striped),
    write(Striped), nl,
    
    % Query: What animal has long neck?
    write('Which animal has long neck? '),
    findall(X, (animal(X,_,_,Features,_), member(long_neck, Features)), LongNeck),
    write(LongNeck), nl,
    
    nl, write('============================================'), nl,
    halt.