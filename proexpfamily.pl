% ============================================
% SIMPLE FAMILY TREE EXPERT SYSTEM
% ============================================

:- initialization(main).

% Facts
parent(john, mary).
parent(john, peter).
parent(mary, susan).
parent(mary, paul).
parent(peter, alice).
parent(peter, bob).
parent(susan, charlie).
parent(paul, eve).

male(john). male(peter). male(paul). male(bob). male(charlie).
female(mary). female(susan). female(alice). female(eve).

% Rules
father(X,Y) :- male(X), parent(X,Y).
mother(X,Y) :- female(X), parent(X,Y).
grandparent(X,Y) :- parent(X,Z), parent(Z,Y).
sibling(X,Y) :- parent(Z,X), parent(Z,Y), X \= Y.

% Main expert system
main :-
    nl, write('=== FAMILY TREE EXPERT SYSTEM ==='), nl, nl,
    write('Facts:'), nl,
    write('  John is father of Mary and Peter'), nl,
    write('  Mary is mother of Susan and Paul'), nl,
    write('  Peter is father of Alice and Bob'), nl,
    write('  Susan is mother of Charlie'), nl,
    write('  Paul is father of Eve'), nl, nl,
    
    write('=== QUERIES ==='), nl,
    
    % Who is father of Mary?
    father(F, mary), write('Father of Mary is: '), write(F), nl,
    
    % Who is mother of Paul?
    mother(M, paul), write('Mother of Paul is: '), write(M), nl,
    
    % Who are children of John?
    findall(C, parent(john, C), JC), write('Children of John: '), write(JC), nl,
    
    % Who are grandchildren of John?
    findall(GC, grandparent(john, GC), JGC), write('Grandchildren of John: '), write(JGC), nl,
    
    % Who are siblings of Mary?
    findall(S, sibling(mary, S), MS), write('Siblings of Mary: '), write(MS), nl,
    
    nl, write('====================================='), nl,
    halt.