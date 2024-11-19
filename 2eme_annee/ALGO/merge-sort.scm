
; merge two sorted list (a & b) into a new one (c)
(define (merge a b c)
  (cond 
   ((null? a) (append (reverse c) b))
   ((null? b) (append (reverse c) a))
   ((<= (car a) (car b))
      (merge (cdr a) b (cons (car a) c)))
   (else (merge a (cdr b) (cons (car b) c)))))



; take n element from lst 
(define (take lst n)
  (if (or (null? lst) (zero? n) (> n (length lst)))
      '()
      (cons (car lst) (take (cdr lst) (- n 1)))))


; return slice of lst from start to end (not included)
(define (slice lst start end)
  (cond
   ((or (null? lst) (> start (- (length lst) 1)))
    '())
   ((>= start 1)
    (slice (cdr lst) (- start 1) (- end 1)))
   (else
    (take lst end))))


; sort a list with merge sort
(define (sort lst)
  (if (null? (cdr lst)) (car lst))
  (merge
   (sort (slice lst 0 (/ (length lst) 2)))
   (sort (slice lst (/ (length lst) 2) (length list)))))


(sort '(7 9 3 54 12 65))
