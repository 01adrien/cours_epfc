using System.ComponentModel.DataAnnotations;

namespace prid_tuto.Models;

public class Member
{
    [Key]
    public string Pseudo { get; set; } = "";
    public string Password { get; set; } = "";
    public string? FullName { get; set; }

    public DateOnly? BirthDate { get; set; }

    public int? Age {
        get {
            if (!BirthDate.HasValue)
                return null;
            var today = DateOnly.FromDateTime(DateTime.Today);
            var age = today.Year - BirthDate.Value.Year;
            if (BirthDate.Value > today.AddYears(-age)) age--;
            return age;
        }
    }
}