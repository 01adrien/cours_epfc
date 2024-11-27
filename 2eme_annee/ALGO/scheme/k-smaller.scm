; guile 2.2.3

; return lst WITHOUT items greater than p
(define (filter-low lst p)
    (filter (lambda (x) (< x p)) lst))


; return lst WITHOUT item lower or equal than p
(define (filter-high lst p)
    (filter (lambda (x) (> x p)) lst))


; return the k smallest item in lst
(define (k-smallest lst k)
    (if (>= k (length lst))
      '()
       (let* 
        ((p (car lst)))
        ((low (filter-low lst p))
        ((high (filter-high lst p)))))
             (cond 
               ((null? (cdr lst)) (car lst))
               ((equal? (length low) k) p)
               ((< (length low) k ) (k-smallest high (- k (length low))))
               (else (k-smallest low k)))))

(display (k-smallest '(1 1 1 2 3 4) 4))
