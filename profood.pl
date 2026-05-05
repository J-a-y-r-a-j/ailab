% ============================================
% VEGETABLE/FRUIT CLASSIFICATION - Simple PROLOG
% ============================================

:- initialization(main).

% ===== FACTS =====

% Fruit facts
fruit(apple).
fruit(banana).
fruit(orange).
fruit(mango).
fruit(grape).
fruit(strawberry).

% Vegetable facts
vegetable(carrot).
vegetable(broccoli).
vegetable(tomato).   % Actually fruit but commonly considered vegetable
vegetable(potato).
vegetable(spinach).
vegetable(onion).

% Color facts
color(apple, red).
color(banana, yellow).
color(orange, orange).
color(mango, yellow).
color(grape, purple).
color(strawberry, red).
color(carrot, orange).
color(broccoli, green).
color(tomato, red).
color(potato, brown).
color(spinach, green).
color(onion, white).

% Taste facts
taste(apple, sweet).
taste(banana, sweet).
taste(orange, sour_sweet).
taste(mango, sweet).
taste(grape, sweet).
taste(strawberry, sweet).
taste(carrot, sweet).
taste(broccoli, bitter).
taste(tomato, sour).
taste(potato, starchy).
taste(spinach, bitter).
taste(onion, pungent).

% Growing season
season(apple, autumn).
season(banana, all_year).
season(orange, winter).
season(mango, summer).
season(grape, summer).
season(strawberry, spring).
season(carrot, all_year).
season(broccoli, winter).
season(tomato, summer).
season(potato, all_year).
season(spinach, spring).
season(onion, all_year).

% ===== RULES =====

% Is it a fruit or vegetable?
is_fruit(X) :- fruit(X).
is_vegetable(X) :- vegetable(X).

% Sweet fruits
sweet_fruit(X) :- fruit(X), taste(X, sweet).

% Red fruits
red_fruit(X) :- fruit(X), color(X, red).

% Yellow vegetables
yellow_vegetable(X) :- vegetable(X), color(X, yellow).

% Summer vegetables
summer_vegetable(X) :- vegetable(X), season(X, summer).

% Sweet vegetables
sweet_vegetable(X) :- vegetable(X), taste(X, sweet).

% Edible raw
edible_raw(apple).
edible_raw(banana).
edible_raw(orange).
edible_raw(strawberry).
edible_raw(carrot).
edible_raw(tomato).

% ===== MAIN PROGRAM =====

main :-
    nl, write('=== FRUIT & VEGETABLE CLASSIFIER ==='), nl, nl,
    
    write('All fruits: '), findall(F, fruit(F), Fruits), write(Fruits), nl,
    write('All vegetables: '), findall(V, vegetable(V), Veggies), write(Veggies), nl, nl,
    
    write('Sweet fruits: '), findall(F, sweet_fruit(F), SweetF), write(SweetF), nl,
    write('Red fruits: '), findall(F, red_fruit(F), RedF), write(RedF), nl,
    write('Yellow vegetables: '), findall(V, yellow_vegetable(V), YellowV), write(YellowV), nl,
    write('Summer vegetables: '), findall(V, summer_vegetable(V), SummerV), write(SummerV), nl,
    write('Sweet vegetables: '), findall(V, sweet_vegetable(V), SweetV), write(SweetV), nl,
    write('Edible raw: '), findall(X, edible_raw(X), Raw), write(Raw), nl, nl,
    
    % Queries
    write('=== QUESTIONS ==='), nl,
    write('Q: Is apple a fruit? '), (fruit(apple) -> write('Yes') ; write('No')), nl,
    write('Q: Is carrot a fruit? '), (fruit(carrot) -> write('Yes') ; write('No')), nl,
    write('Q: What color is banana? '), color(banana, C), write(C), nl,
    write('Q: What tastes sweet? '), findall(X, taste(X, sweet), Sweet), write(Sweet), nl,
    write('Q: What is red? '), findall(X, color(X, red), Red), write(Red), nl,
    
    nl, write('====================================='), nl,
    halt.