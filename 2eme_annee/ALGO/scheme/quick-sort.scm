; guile 2.2.3

; return lst without one occurence of item
(define (delete item lst)
    (cond
     ((null? lst) '())
     ((equal? item (car lst)) (cdr lst))
     (else (cons (car lst) (delete item (cdr lst))))))


; return lst WITHOUT items greater than p
(define (filter-low lst p)
    (filter (lambda (x) (<= x p)) lst))


; return lst WITHOUT item lower or equal than p
(define (filter-high lst p)
    (filter (lambda (x) (> x p)) lst))


; sort lst with quick sort
(define (qsort lst)
    (if (or (null? lst) (null? (cdr lst)))
        lst
        (let ((p (car lst)))
            (append (qsort (delete p (filter-low lst p))) 
                    (append `(,p) (qsort (filter-high lst p)))))))


(display (qsort '(15 18 2 9 65 23 45 2 45 9 2)))