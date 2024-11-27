(define name car)
(define neighbor cadr)
(define first car)
(define rest cdr)

(define graph 
  '(("A" (5)) ("F" (4 5 6 7 1)) ("B" (4 2))
    ("C" (5 4)) ("G" (4)) ("E" (5)) ("U" (4))))

(define visited '())

(define (visit summit)
  (if (and (not (null? summit)) (not (visited? summit)))
      (and 
        (display (name summit))
	      (newline)
	      (set! visited (cons (name summit) visited)))))


(define (visited? summit)
  (member (name summit) visited))


(define (still-neighbor? summit)
  (not (equal? (length (neighbor summit)) 0)))


(define (closest summit)
  (if (null? summit)
      '()
      (list-ref graph (- (first (neighbor summit)) 1))))


(define (depth-first summit)
    (visit summit)
    (if (still-neighbor? summit)
      (let ((next-summit (closest summit)))
          (if (not (visited? next-summit)) 
            (depth-first next-summit))
          (depth-first `(,(name summit) ,(rest (neighbor summit)))))))
        
    
    


(newline)
(depth-first (list-ref graph 1))
(newline)
(display visited)

