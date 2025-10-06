using FluentValidation;
using Microsoft.EntityFrameworkCore;

namespace prid_tuto.Models;

public class MemberValidator : AbstractValidator<Member>
{
    private readonly MsnContext _context;

    public MemberValidator(MsnContext context) {
        _context = context;

        RuleFor(m => m.Pseudo)
            .NotEmpty()
            .MinimumLength(3);

        RuleFor(m => m.Password)
            .NotEmpty()
            .MinimumLength(3);

        RuleFor(m => m.FullName)
            .MinimumLength(3);

        RuleFor(m => new { m.Pseudo, m.FullName })
            .MustAsync((m, token) => BeUniqueFullName(m.Pseudo, m.FullName, token))
            .OverridePropertyName(nameof(Member.FullName))
            .WithMessage("'{PropertyName}' must be unique.");

        RuleFor(m => m.BirthDate)
            .LessThan(DateOnly.FromDateTime(DateTime.Today))
            .DependentRules(() => {
                RuleFor(m => m.Age)
                    .GreaterThanOrEqualTo(18);
            });

        // Validations spécifiques pour la création
        RuleSet("create", () => {
            RuleFor(m => m.Pseudo)
                .MustAsync(BeUniquePseudo)
                .WithMessage("'{PropertyName}' must be unique.");
        });
    }

    public async Task<FluentValidation.Results.ValidationResult> ValidateOnCreate(Member member) {
        return await this.ValidateAsync(member, o => o.IncludeRuleSets("default", "create"));
    }

    private async Task<bool> BeUniquePseudo(string pseudo, CancellationToken token) {
        return !await _context.Members.AnyAsync(m => m.Pseudo == pseudo, token);
    }

    private async Task<bool> BeUniqueFullName(string pseudo, string? fullName, CancellationToken token) {
		return string.IsNullOrEmpty(fullName) || 
			!await _context.Members.AnyAsync(m => m.Pseudo != pseudo && m.FullName == fullName, token);
    }
}