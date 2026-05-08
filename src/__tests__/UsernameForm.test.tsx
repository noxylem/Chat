import "@testing-library/jest-dom/jest-globals";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import UsernameForm from "@/components/UsernameForm";

describe("UsernameForm", () => {
  it("renders the form with username and room inputs", () => {
    render(<UsernameForm onSubmit={jest.fn()} />);

    expect(screen.getByLabelText("Username")).toBeInTheDocument();
    expect(screen.getByLabelText("Room")).toBeInTheDocument();
    expect(
      screen.getByRole("button", { name: "Join Chat" })
    ).toBeInTheDocument();
  });

  it("disables the submit button when username is empty", () => {
    render(<UsernameForm onSubmit={jest.fn()} />);

    const button = screen.getByRole("button", { name: "Join Chat" });
    expect(button).toBeDisabled();
  });

  it("enables the submit button when username is provided", async () => {
    const user = userEvent.setup();
    render(<UsernameForm onSubmit={jest.fn()} />);

    await user.type(screen.getByLabelText("Username"), "Alice");

    const button = screen.getByRole("button", { name: "Join Chat" });
    expect(button).toBeEnabled();
  });

  it("calls onSubmit with username and room on form submission", async () => {
    const onSubmit = jest.fn();
    const user = userEvent.setup();
    render(<UsernameForm onSubmit={onSubmit} />);

    await user.type(screen.getByLabelText("Username"), "Alice");
    await user.clear(screen.getByLabelText("Room"));
    await user.type(screen.getByLabelText("Room"), "dev");
    await user.click(screen.getByRole("button", { name: "Join Chat" }));

    expect(onSubmit).toHaveBeenCalledWith("Alice", "dev");
  });

  it("uses 'general' as default room", async () => {
    const onSubmit = jest.fn();
    const user = userEvent.setup();
    render(<UsernameForm onSubmit={onSubmit} />);

    await user.type(screen.getByLabelText("Username"), "Alice");
    await user.click(screen.getByRole("button", { name: "Join Chat" }));

    expect(onSubmit).toHaveBeenCalledWith("Alice", "general");
  });

  it("displays the app title and description", () => {
    render(<UsernameForm onSubmit={jest.fn()} />);

    expect(screen.getByText("Chat")).toBeInTheDocument();
    expect(
      screen.getByText("Join a room and start chatting")
    ).toBeInTheDocument();
  });
});
