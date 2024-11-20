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
       (let ((p (car lst)))
           (let ((low (filter-low lst p))
                 (high (filter-high lst p)))
             (cond 
               ((null? (cdr lst)) (car lst))
               ((equal? (length low) k) p)
               ((< (length low) k ) (k-smallest high k))
               (else (k-smallest low k)))))))

(display (k-smallest-v1 '(1 1 1) 6))
