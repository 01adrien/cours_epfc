(define (foldr reducer acc lst)
    (if (null? lst) acc
        (foldr reducer (reducer (car lst) acc) (cdr lst))))

(define (foldl reducer acc lst)
    '())

(display (foldr (lambda (val acc) (+ val acc)) 0 `(1 2 3 4)))