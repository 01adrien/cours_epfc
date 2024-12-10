(define-module (main)
  #:use-module (ice-9 match)
  #:use-module (srfi srfi-1)
  #:use-module (srfi srfi-13))

(define graph '((EUR USD 1.1)
		(USD JPY 145)
		(JPY EUR .007)
		(EUR GPB .85)
		(GPB USD 1.17)
		(USD AUD 1.55)
		(AUD EUR .64)
		(CAD USD .74)
		(CAD EUR .67)))


(define (links-of graph currency)
  "return a list of currencys accessible from currency in arg"
  (define (loop graph lst)
    (match graph
      [`() lst]
      [`((,from ,to ,rate) . ,rest)
       (if (equal? from currency)
	   (loop rest (cons `("to",to"rate",rate) lst))
	   (loop rest lst))]))
  (loop graph '()))


(define (neg-logs . nodes)
  "return a list with all negate log of nodes in arg"
  (define (loop nodes lst)
    (match nodes
      [`() lst]
      [`((,from ,to ,rate) . ,rest)
       (loop rest (cons (- (log rate)) lst))]))
  (loop nodes '()))

(define lst (neg-logs
	     (list-ref graph 0)
	     (list-ref graph 1)
	     (list-ref graph 2)))

(display lst)
(newline)
(display (apply + lst))
(newline)

'()
