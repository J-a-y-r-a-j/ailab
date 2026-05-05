% ============================================
% SIMPLE JOB MATCHING EXPERT SYSTEM
% ============================================

:- initialization(main).

% Facts: job(JobName, RequiredSkills, MinYears, Salary)
job(software_developer, [programming, problem_solving], 2, 60000).
job(data_scientist, [python, statistics, ml], 3, 80000).
job(web_designer, [html, css, javascript], 1, 50000).
job(project_manager, [leadership, communication], 5, 70000).

% Person: person(Name, Skills, Experience)
person(john, [programming, problem_solving, python], 3).
person(mary, [python, statistics, ml, sql], 4).
person(tom, [html, css, javascript, design], 2).
person(lisa, [leadership, communication, planning], 6).

% Rules
qualifies(Person, Job) :-
    person(Person, PSkills, PExp),
    job(Job, JSkills, JExp, _),
    PExp >= JExp,
    subset(JSkills, PSkills).

subset([], _).
subset([H|T], List) :- member(H, List), subset(T, List).

% Main
main :-
    nl, write('=== JOB MATCHING EXPERT SYSTEM ==='), nl, nl,
    
    write('Job requirements:'), nl,
    forall(job(J, Skills, Exp, Sal), 
        format('~w: ~w years, skills ~w, salary $~w~n', [J, Exp, Skills, Sal])), nl,
    
    write('Matching results:'), nl,
    forall(person(P, _, _), (
        format('~w can work as: ', [P]),
        findall(J, qualifies(P, J), Jobs),
        (Jobs = [] -> write('None') ; write(Jobs)),
        nl
    )), nl,
    
    write('====================================='), nl,
    halt.