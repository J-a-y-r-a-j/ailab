% ============================================
% SIMPLE DISEASE CLASSIFICATION EXPERT SYSTEM
% ============================================

:- initialization(main).

% Disease facts: disease(Name, Symptoms, Severity)
disease(common_cold, [fever, runny_nose, sneezing, sore_throat], mild).
disease(flu, [fever, headache, body_ache, fatigue, cough], moderate).
disease(covid, [fever, cough, fatigue, loss_of_taste, difficulty_breathing], severe).
disease(allergy, [sneezing, runny_nose, itchy_eyes], mild).
disease(migraine, [headache, nausea, sensitivity_to_light], moderate).
disease(strep_throat, [sore_throat, fever, swollen_glands], moderate).
disease(pneumonia, [fever, cough, chest_pain, difficulty_breathing], severe).
disease(food_poisoning, [nausea, vomiting, diarrhea, stomach_cramps], moderate).

% Rule: Find disease by symptoms
diagnose(Symptoms, Disease) :-
    disease(Disease, DiseaseSymptoms, Severity),
    subset(DiseaseSymptoms, Symptoms),
    format('~n=== DIAGNOSIS ===~n', []),
    format('Disease: ~w~n', [Disease]),
    format('Severity: ~w~n', [Severity]).

subset([], _).
subset([H|T], List) :- member(H, List), subset(T, List).

% Pre-defined patient cases
patient(john, [fever, runny_nose, sneezing, sore_throat]).
patient(mary, [fever, headache, body_ache, fatigue]).
patient(tom, [fever, cough, loss_of_taste, fatigue]).
patient(lisa, [sneezing, runny_nose, itchy_eyes]).
patient(bob, [headache, nausea, sensitivity_to_light]).
patient(sue, [nausea, vomiting, diarrhea, stomach_cramps]).

% Main
main :-
    nl, write('=== DISEASE CLASSIFICATION EXPERT SYSTEM ==='), nl, nl,
    
    write('=== DISEASE DATABASE ==='), nl,
    forall(disease(D, Sym, Sev), 
        format('~w: ~w (~w)~n', [D, Sym, Sev])), nl,
    
    write('=== PATIENT DIAGNOSES ==='), nl,
    forall(patient(P, Symptoms), (
        format('~w symptoms: ~w~n', [P, Symptoms]),
        (diagnose(Symptoms, Disease) -> true ; format('No diagnosis found~n')),
        nl
    )),
    
    write('============================================'), nl,
    halt.