#include <stdio.h>
#include <stdlib.h>

int main(int argc, char const* argv[])
{
  typedef struct {
    int a;
    short s[2];
  } MSG;
  MSG *mp, m = {4, 1, 0};
  short *fp, *tp;
  mp = (MSG*) malloc(sizeof(MSG));
  for (fp = (short*) m.s, tp = (short*) mp->s; tp < (short*) (mp + 1);)
    *tp++ = *fp++;
  printf("%d\n", sizeof(short));
  printf("%d %d\n", m.s[0], m.s[1]);
  printf("%d %d\n", *mp->s, *(mp->s + 1));
}
