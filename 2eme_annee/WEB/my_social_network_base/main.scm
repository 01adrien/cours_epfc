(define (kons a b) (lambda (p) (p a b)))
(define (kar x) (x (lambda (a b) a)))
(define (kdr x) (x (lambda (a b) b)))