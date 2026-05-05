% ============================================
% SIMPLE SENTIMENT ANALYSIS EXPERT SYSTEM
% ============================================

:- initialization(main).

% Positive words
pos(good). pos(great). pos(excellent). pos(amazing).
pos(happy). pos(love). pos(like). pos(nice). pos(best).

% Negative words  
neg(bad). neg(terrible). neg(awful). neg(horrible).
neg(hate). neg(sad). neg(worst). neg(poor).

% Intensifiers
intense(very). intense(really). intense(extremely).

% Negations
not_word(not). not_word(never).

% Calculate score for single word
word_score(Word, 1) :- pos(Word).
word_score(Word, -1) :- neg(Word).
word_score(_, 0).

% Check if word has negation before it
has_negation(Sentence, Index) :-
    Index > 0,
    nth0(PrevIndex, Sentence, PrevWord),
    PrevIndex is Index - 1,
    not_word(PrevWord).

% Calculate sentence score
sentence_score(Sentence, Score) :-
    findall(ScoreValue, (
        nth0(Index, Sentence, Word),
        word_score(Word, Base),
        (has_negation(Sentence, Index) -> Value is -Base ; Value = Base),
        ScoreValue = Value
    ), Scores),
    sum_list(Scores, Score).

% Classify sentiment
sentiment(Sentence, positive) :- sentence_score(Sentence, Score), Score > 0.
sentiment(Sentence, negative) :- sentence_score(Sentence, Score), Score < 0.
sentiment(Sentence, neutral) :- sentence_score(Sentence, Score), Score =:= 0.

% Print result
print_result(Sentence) :-
    sentence_score(Sentence, Score),
    sentiment(Sentence, Sentiment),
    format('"~w" -> Score: ~w (~w)~n', [Sentence, Score, Sentiment]).

% Main
main :-
    nl, write('=== SIMPLE SENTIMENT ANALYSIS ==='), nl, nl,
    
    write('Examples:'), nl,
    print_result([this, is, good]),
    print_result([this, is, bad]),
    print_result([very, good]),
    print_result([not, good]),
    print_result([i, love, it]),
    print_result([i, hate, it]),
    print_result([it, is, okay]),
    print_result([really, bad]),
    print_result([not, bad]),
    
    nl, write('====================================='), nl,
    halt.