
(define min-array (array)
  (if (null? (cdr array))
      (car array)
      (min (car array) (min-array (cdr array)))))


(define (extract item array) 
  (if (equal? (car array) item)
      (cdr array)
      (cons (car array) (extract item (cdr array)))))


(define (selection-sort array)
  (if (null? array)
      '()
      (let ((m (min-array array)))
        (cons m (selection-sort (extract m array))))))


