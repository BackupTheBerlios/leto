package org.eu.leto.swing;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;


/**
 * Patched version of JTable. Fix the following bugs:
 * <ul>
 * <li><a
 * href="http://jroller.com/page/santhosh/20050524#jtable_becomes_uglier_with_auto">JTable
 * becomes uglier with AUTO_RESIZE_OFF</a></li>
 * <li><a
 * href="http://jroller.com/page/santhosh/20050523#fit_tablecolumns_on_demand">Fit
 * TableColumns on Demand</a></li>
 * </ul>
 */
class PatchedJTable extends JTable {
    private final JPanel headerPanel = new JPanel(new BorderLayout());
    private final JLabel dummyColumn = new JLabel() {
        public Border getBorder() {
            return UIManager.getBorder("TableHeader.cellBorder");
        }
    };
    private final MouseListener viewPortMouseListener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            repostEvent(e);
        }


        public void mousePressed(MouseEvent e) {
            repostEvent(e);
        }


        public void mouseReleased(MouseEvent e) {
            repostEvent(e);
        }


        public void mouseEntered(MouseEvent e) {
            repostEvent(e);
        }


        public void mouseExited(MouseEvent e) {
            repostEvent(e);
        }


        private void repostEvent(MouseEvent e) {
            MouseEvent e2 = SwingUtilities.convertMouseEvent(e.getComponent(),
                    e, PatchedJTable.this);
            dispatchEvent(e2);
        }
    };


    @Override
    public boolean getScrollableTracksViewportHeight() {
        return getPreferredSize().height < getParent().getHeight();
    }


    @Override
    protected void configureEnclosingScrollPane() {
        final Container p = getParent();
        if (p instanceof JViewport) {
            final Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                final JScrollPane scrollPane = (JScrollPane) gp;
                final JViewport viewport = scrollPane.getViewport();

                if (getAutoResizeMode() != 0) {
                    super.configureEnclosingScrollPane();
                } else {
                    headerPanel.removeAll();
                    headerPanel.add(getTableHeader(), BorderLayout.WEST);
                    headerPanel.add(dummyColumn, BorderLayout.CENTER);
                    scrollPane.setColumnHeaderView(headerPanel);

                    viewport.setBackground(getBackground());
                    viewport.addMouseListener(viewPortMouseListener);
                }

                scrollPane.setBorder(BorderFactory.createEmptyBorder());
            }
        }

    }


    @Override
    protected void unconfigureEnclosingScrollPane() {
        final Container p = getParent();
        if (p instanceof JViewport) {
            final Container gp = p.getParent();
            if (gp instanceof JScrollPane) {
                final JScrollPane scrollPane = (JScrollPane) gp;
                final JViewport viewport = scrollPane.getViewport();
                if (viewport != null && viewport.getView() == this) {
                    scrollPane.setColumnHeaderView(null);
                    viewport.removeMouseListener(viewPortMouseListener);
                }
            }
        }
    }


    @Override
    protected JTableHeader createDefaultTableHeader() {
        final JTableHeader tableHeader = super.createDefaultTableHeader();
        tableHeader.addMouseListener(new ColumnFitAdapter());
        return tableHeader;
    }

    private class ColumnFitAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                JTableHeader header = (JTableHeader) e.getSource();
                TableColumn tableColumn = getResizingColumn(header, e
                        .getPoint());
                if (tableColumn == null)
                    return;
                int col = header.getColumnModel().getColumnIndex(
                        tableColumn.getIdentifier());
                JTable table = header.getTable();
                int rowCount = table.getRowCount();
                int width = (int) header.getDefaultRenderer()
                        .getTableCellRendererComponent(table,
                                tableColumn.getIdentifier(), false, false, -1,
                                col).getPreferredSize().getWidth();
                for (int row = 0; row < rowCount; row++) {
                    int preferedWidth = (int) table.getCellRenderer(row, col)
                            .getTableCellRendererComponent(table,
                                    table.getValueAt(row, col), false, false,
                                    row, col).getPreferredSize().getWidth();
                    width = Math.max(width, preferedWidth);
                }
                // the next line is very important
                header.setResizingColumn(tableColumn);
                tableColumn.setWidth(width + table.getIntercellSpacing().width);
            }
        }


        // copied from BasicTableHeader.MouseInputHandler.getResizingColumn
        private TableColumn getResizingColumn(JTableHeader header, Point p) {
            return getResizingColumn(header, p, header.columnAtPoint(p));
        }


        // copied from BasicTableHeader.MouseInputHandler.getResizingColumn
        private TableColumn getResizingColumn(JTableHeader header, Point p,
                int column) {
            if (column == -1) {
                return null;
            }
            Rectangle r = header.getHeaderRect(column);
            r.grow(-3, 0);
            if (r.contains(p))
                return null;
            int midPoint = r.x + r.width / 2;
            int columnIndex;
            if (header.getComponentOrientation().isLeftToRight())
                columnIndex = (p.x < midPoint) ? column - 1 : column;
            else
                columnIndex = (p.x < midPoint) ? column : column - 1;
            if (columnIndex == -1)
                return null;
            return header.getColumnModel().getColumn(columnIndex);
        }
    }
}
