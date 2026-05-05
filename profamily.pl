% ============================================
% FAMILY TREE - Complete PROLOG Program with Auto-Run
% ============================================

:- initialization(main).

% ===== FACTS =====

% Parent facts
parent(john, mary).
parent(john, peter).
parent(mary, susan).
parent(mary, paul).
parent(peter, alice).
parent(peter, bob).
parent(susan, charlie).
parent(susan, diana).
parent(paul, eve).
parent(paul, frank).

% Gender facts
male(john).
male(peter).
male(paul).
male(bob).
male(charlie).
male(frank).

female(mary).
female(susan).
female(alice).
female(eve).
female(diana).

% ===== RULES =====

parent(P, C) :- parent(P, C).

grandparent(GP, GC) :-
    parent(GP, P),
    parent(P, GC).

sibling(X, Y) :-
    parent(P, X),
    parent(P, Y),
    X \= Y.

father(F, C) :-
    male(F),
    parent(F, C).

mother(M, C) :-
    female(M),
    parent(M, C).

son(S, P) :-
    male(S),
    parent(P, S).

daughter(D, P) :-
    female(D),
    parent(P, D).

uncle(U, N) :-
    male(U),
    parent(P, N),
    sibling(U, P).

aunt(A, N) :-
    female(A),
    parent(P, N),
    sibling(A, P).

ancestor(A, D) :-
    parent(A, D).
ancestor(A, D) :-
    parent(A, X),
    ancestor(X, D).

descendant(D, A) :-
    ancestor(A, D).

% ===== QUERY FUNCTIONS =====

% Print with formatting
print_relation(Relation, Results) :-
    write(Relation), write(': '),
    write(Results), nl.

% Find all children of a parent
children_of(Parent, Children) :-
    findall(Child, parent(Parent, Child), Children).

% Find all parents of a child
parents_of(Child, Parents) :-
    findall(Parent, parent(Parent, Child), Parents).

% Find all siblings of a person
siblings_of(Person, Siblings) :-
    findall(Sib, (sibling(Person, Sib), Person \= Sib), Siblings).

% Find all grandchildren of a person
grandchildren_of(GP, GrandChildren) :-
    findall(GC, grandparent(GP, GC), GrandChildren).

% ===== MAIN PROGRAM =====

main :-
    nl, write('============================================'), nl,
    write('        FAMILY TREE PREDICATE LOGIC'), nl,
    write('============================================'), nl, nl,
    
    % Display tree
    write('=== FAMILY TREE ==='), nl,
    write('John (father)'), nl,
    write('├── Mary (daughter)'), nl,
    write('│   ├── Susan (daughter)'), nl,
    write('│   │   ├── Charlie (son)'), nl,
    write('│   │   └── Diana (daughter)'), nl,
    write('│   └── Paul (son)'), nl,
    write('│       ├── Eve (daughter)'), nl,
    write('│       └── Frank (son)'), nl,
    write('└── Peter (son)'), nl,
    write('    ├── Alice (daughter)'), nl,
    write('    └── Bob (son)'), nl, nl,
    
    % Show relationships
    write('=== RELATIONSHIPS ==='), nl,
    
    % Parents
    write('1. PARENTS:'), nl,
    forall(parent(P, C), format('   ~w is parent of ~w~n', [P, C])), nl,
    
    % Children
    write('2. CHILDREN:'), nl,
    forall((parent(P, C), \+ (parent(P, _), _)), 
           (children_of(P, Kids), format('   ~w has children: ~w~n', [P, Kids]))),
    nl,
    
    % Grandparents
    write('3. GRANDPARENTS:'), nl,
    forall(grandparent(GP, GC), 
           format('   ~w is grandparent of ~w~n', [GP, GC])), nl,
    
    % Siblings
    write('4. SIBLINGS:'), nl,
    forall((sibling(X, Y), X @< Y), 
           format('   ~w and ~w are siblings~n', [X, Y])), nl,
    
    % Queries
    write('=== SPECIFIC QUERIES ==='), nl,
    
    % Children of John
    children_of(john, JohnKids),
    format('   Children of John: ~w~n', [JohnKids]),
    
    % Parents of Alice
    parents_of(alice, AliceParents),
    format('   Parents of Alice: ~w~n', [AliceParents]),
    
    % Siblings of Mary
    siblings_of(mary, MarySibs),
    format('   Siblings of Mary: ~w~n', [MarySibs]),
    
    % Grandchildren of John
    grandchildren_of(john, JohnGrandKids),
    format('   Grandchildren of John: ~w~n', [JohnGrandKids]),
    
    % Father of Susan
    father(FatherSusan, susan),
    format('   Father of Susan: ~w~n', [FatherSusan]),
    
    % Mother of Paul
    mother(MotherPaul, paul),
    format('   Mother of Paul: ~w~n', [MotherPaul]),
    
    % Uncles of Alice
    findall(U, uncle(U, alice), UnclesAlice),
    format('   Uncles of Alice: ~w~n', [UnclesAlice]),
    
    % Aunts of Bob
    findall(A, aunt(A, bob), AuntsBob),
    format('   Aunts of Bob: ~w~n', [AuntsBob]),
    
    % Ancestors of Charlie
    findall(A, ancestor(A, charlie), AncestorsCharlie),
    format('   Ancestors of Charlie: ~w~n', [AncestorsCharlie]),
    
    % Descendants of John
    findall(D, descendant(D, john), DescendantsJohn),
    format('   Descendants of John: ~w~n', [DescendantsJohn]),
    
    nl, write('============================================'), nl,
    write('Program completed!'), nl,
    halt.