(define (merge a b c)
  (cond 
   ((null? a) (append (reverse c) b))
   ((null? b) (append (reverse c) a))
   ((<= (car a) (car b))
      (merge (cdr a) b (cons (car a) c)))
   (else (merge a (cdr b) (cons (car b) c)))))

(define (slice start end)
  '())

(merge `(1) `(3 5 9 14) '())
