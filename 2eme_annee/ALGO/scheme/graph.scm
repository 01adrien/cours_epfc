(define get-data car)
(define get-neighbor cadr)


(define graph 
    '(("A" (2 3 4)) ("F" (1 3 5 6)) ("B" (1 2 6))
      ("C" (1 7 8)) ("G" (2 6)) ("E" (2 3 5)) ("U" (4 8) (4 7))))

(define visited '())

(define (visit summit)
    (display (get-data summit))
    (set! visited (cons (get-data summit) visited)))

(define (visited? summit)
    (member? (summit) visited))

(define (depth-first summit)
    (visit summit)
    (if )
    (let* ((next-summit-index car (get-neighbor summit))
           (next-summit (get-data (list-ref graph next-summit-i))))
        (cond 
            ((not (visited? next-summit))
                (and (visit next-summit)))
            ((visited? summit new-visited-list)
                (depth-first graph  )))))
        


(display visited)
(newline)
(visit (list-ref graph 1))
(newline)
(display visited)