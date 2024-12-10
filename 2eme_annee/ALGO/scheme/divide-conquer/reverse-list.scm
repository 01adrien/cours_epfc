(define-module (main)
  #:use-module (ice-9 match)
  #:use-module (srfi srfi-1)
  #:use-module (srfi srfi-13))



(define (reverse-lst lst)
  "reverse a list with divide and conquer"
  (if (or (null? lst) (null? (cdr lst)))
      lst
      (append (reverse-lst (cdr lst)) (list (car lst)))))


(reverse-lst '(1 2 3 4 5 6))
