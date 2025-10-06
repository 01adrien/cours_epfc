using Microsoft.EntityFrameworkCore;

namespace prid_tuto.Models;

public class MsnContext(DbContextOptions<MsnContext> options) : DbContext(options)
{
    protected override void OnModelCreating(ModelBuilder modelBuilder) {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Member>().HasData(
            new Member { Pseudo = "ben", Password = "ben", FullName = "Benoît Penelle" },
            new Member { Pseudo = "bruno", Password = "bruno", FullName = "Bruno Lacroix" }
        );
    }

    public DbSet<Member> Members => Set<Member>();
}